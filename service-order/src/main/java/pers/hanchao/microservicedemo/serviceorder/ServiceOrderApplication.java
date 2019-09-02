package pers.hanchao.microservicedemo.serviceorder;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.HystrixStreamEndpoint;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

/**
 * @author hanchao
 */
@EnableHystrixDashboard
@EnableHystrix
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class ServiceOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }

    /**
     * https://www.cnblogs.com/x1mercy/p/9291348.html
     * 流式： http://127.0.0.1:6061/hystrix.stream
     * 图形： http://127.0.0.1:6061/hystrix  (参数：http://127.0.0.1:6061/hystrix.stream) (刷新流：http://127.0.0.1:6061/order/1)
     * @see HystrixMetricsStreamServlet
     * @see HystrixStreamEndpoint
     */
    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<>(new HystrixMetricsStreamServlet());
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName(HystrixMetricsStreamServlet.class.getSimpleName());
        return registrationBean;
    }
}
