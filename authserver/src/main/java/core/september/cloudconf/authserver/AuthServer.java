/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package core.september.cloudconf.authserver;

import javax.servlet.*;

import core.september.cloudconf.authserver.filter.JWTFilter;
import core.september.cloudconf.authserver.service.JWTService;
import core.september.cloudconf.authserver.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class AuthServer {

    @Autowired
    LogService logService;

    @Autowired
    JWTService service;
	//private static Log logger = LogFactory.getLog(AuthServer.class);

	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				logService.getLogger(getClass()).info("ServletContext initialized");
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
                logService.getLogger(getClass()).info("ServletContext destroyed");
			}

		};
	}

    @Bean
    public FilterRegistrationBean jwtFilter() {
        Filter filter = new JWTFilter(service);
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(filter);
        return bean;
    }

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AuthServer.class, args);
	}

}
