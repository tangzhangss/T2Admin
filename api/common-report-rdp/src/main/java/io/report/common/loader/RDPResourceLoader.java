package io.report.common.loader;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class RDPResourceLoader extends DefaultResourceLoader {
    private ClassLoader cl = new RDPClassloader();
    @Override
    public Resource getResource(String location) {
        return super.getResource(location);
    }
    @Override
    public ClassLoader getClassLoader() {
        return cl;
    }
}