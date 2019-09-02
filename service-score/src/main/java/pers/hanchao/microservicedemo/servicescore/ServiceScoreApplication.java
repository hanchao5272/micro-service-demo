package pers.hanchao.microservicedemo.servicescore;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.HystrixStreamEndpoint;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

/**
 * @author hanchao
 */
@EnableHystrixDashboard
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class ServiceScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceScoreApplication.class, args);
    }


    /**
     * https://www.cnblogs.com/x1mercy/p/9291348.html
     * 流式： http://127.0.0.1:4041/hystrix.stream
     * 图形： http://127.0.0.1:4041/hystrix  (参数：http://127.0.0.1:4041/hystrix.stream) (刷新流：http://127.0.0.1:4041/score/1)
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
