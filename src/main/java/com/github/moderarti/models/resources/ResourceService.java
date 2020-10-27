package com.github.moderarti.models.resources;

public class ResourceService implements ResourceServiceMBean {

    private TestResource resource;

    public TestResource getResource() {
        return resource;
    }

    public void setResource(TestResource resource) {
        this.resource = resource;
    }

    @Override
    public String getName() {
        return resource.getName();
    }

    @Override
    public int getAge() {
        return resource.getAge();
    }
}
