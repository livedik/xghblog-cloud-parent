package com.blog.gateway.spring.server.locator;

import com.blog.base.client.model.entity.RouteGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 动态加载自定义路由
 */
@Slf4j
public class JdbcRouteDefinitionLocator implements ApplicationListener<RemoteApplicationEvent> , ApplicationEventPublisherAware {


    private JdbcTemplate jdbcTemplate;

    private InMemoryRouteDefinitionRepository repository;

    private ApplicationEventPublisher publisher;


    private static final String SELECT_ROUTES_SQL = "SELECT * FROM route_gateway WHERE status = 1";

    @Override
    public void onApplicationEvent(RemoteApplicationEvent remoteRefreshRouteEvent) {
        refresh();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public JdbcRouteDefinitionLocator(JdbcTemplate jdbcTemplate, InMemoryRouteDefinitionRepository repository) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = repository;
    }

    /**
     * 刷新路由
     *
     * @return
     */
    public Mono<Void> refresh() {
        this.loadRoutes();
        // 触发默认路由刷新事件,刷新缓存路由
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return Mono.empty();
    }


    public Mono<Void> loadRoutes(){
//        从数据库查询路由信息
        List<RouteGateway> routeGateways = jdbcTemplate.query(SELECT_ROUTES_SQL, new RowMapper<RouteGateway>() {

            @Override
            public RouteGateway mapRow(ResultSet rs, int i) throws SQLException {
                RouteGateway result = new RouteGateway();
                result.setRouteId(rs.getLong("route_id"));
                result.setRoutePath(rs.getString("route_path"));
                result.setServiceId(rs.getString("service_id"));
                result.setStatus(rs.getInt("status"));
                result.setRetryable(rs.getInt("retryable"));
                result.setStripPrefix(rs.getInt("strip_prefix"));
                result.setIsPersist(rs.getInt("is_persist"));
                result.setRouteName(rs.getString("route_name"));
                return result;
            }

        });

        if (routeGateways!=null)
        {
//            加载路由
            routeGateways.forEach(routeGateway -> {
                RouteDefinition routeDefinition = new RouteDefinition();

                List<PredicateDefinition> pres = Lists.newArrayList();

                List<FilterDefinition> fils = Lists.newArrayList();

                PredicateDefinition predicateDefinition = new PredicateDefinition();
                Map<String,String> preMap = new HashMap<>();
                predicateDefinition.setName("Path");
                preMap.put("name", routeGateway.getRouteName());
                preMap.put("pattern", routeGateway.getRoutePath());
                preMap.put("pathPattern", routeGateway.getRoutePath());
                predicateDefinition.setArgs(preMap);
                pres.add(predicateDefinition);

                FilterDefinition filterDefinition = new FilterDefinition();
                Map<String,String> filMap = new HashMap<>();
                filterDefinition.setName("StripPrefix");
                filMap.put(NameUtils.GENERATED_NAME_PREFIX + "0", "1");
                filterDefinition.setArgs(filMap);
                fils.add(filterDefinition);

//                路由ID
                routeDefinition.setId(routeGateway.getRouteName());
                // 服务地址
                URI uri = UriComponentsBuilder.fromUriString("lb://" + routeGateway.getServiceId()).build().toUri();

                routeDefinition.setUri(uri);
                routeDefinition.setPredicates(pres);
                routeDefinition.setFilters(fils);

                this.repository.save(Mono.just(routeDefinition)).subscribe();
            });
        }

        return Mono.empty();
    }
}
