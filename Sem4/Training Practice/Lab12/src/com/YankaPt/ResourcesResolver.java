package com.YankaPt;

import java.io.InputStream;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

public class ResourcesResolver implements LSResourceResolver {

    private String path;

    public ResourcesResolver(String path) {
        this.path = path;
    }

    @Override
    public LSInput resolveResource(String type, String namespaceURI,
                                   String publicId, String systemId, String baseURI) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(path + systemId);
        return new Input(publicId, systemId, resourceAsStream) {
        };
    }
}
