package com.chao.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * core.io 包主要用于处理资源加载流
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
}
