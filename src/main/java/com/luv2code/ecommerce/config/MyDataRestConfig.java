package com.luv2code.ecommerce.config;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import com.luv2code.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for Product: PUT, POST, DELETE and PATCH
        disableHttpMethod(config.getExposureConfiguration()
                .forDomainType(Product.class), theUnsupportedActions);

        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
        disableHttpMethod(config.getExposureConfiguration()
                .forDomainType(ProductCategory.class), theUnsupportedActions);

        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
        disableHttpMethod(config.getExposureConfiguration()
                .forDomainType(Country.class), theUnsupportedActions);

        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
        disableHttpMethod(config.getExposureConfiguration()
                .forDomainType(State.class), theUnsupportedActions);

        exposeIds(config);
    }

    private static void disableHttpMethod(ExposureConfigurer config, HttpMethod[] theUnsupportedActions) {
        config
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config){
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        for(EntityType tempEntityType : entities){
            entityClasses.add(tempEntityType.getJavaType());
        }

        Class[] domainType = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainType);
    }
}