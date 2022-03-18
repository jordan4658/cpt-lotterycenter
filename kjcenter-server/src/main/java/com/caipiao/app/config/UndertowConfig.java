package com.caipiao.app.config;//package com.caipiao.app.config;
//
//import io.undertow.Undertow;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class UndertowConfig {
//
//	private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//	public static final int IO_THREADS = 8;
//	public static final int WORKER_THREADS = 1000;
//
//	@Bean
//	public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
//
//	    UndertowEmbeddedServletContainerFactory undertowFactory = new UndertowEmbeddedServletContainerFactory();
//
//	    // 这里也可以做其他配置
//	    undertowFactory.addBuilderCustomizers(builder -> {
//			builder.setIoThreads(IO_THREADS);
//			builder.setWorkerThreads(WORKER_THREADS);
//			builder.setBufferSize(1024);
//			//builder.setBuffersPerRegion(1024);
//			builder.setDirectBuffers(true);
//	    });
//	    log.info("-------undertowFactory port : [{}]--------", undertowFactory.getPort());
//	    return undertowFactory;
//	}
//
//
//}
