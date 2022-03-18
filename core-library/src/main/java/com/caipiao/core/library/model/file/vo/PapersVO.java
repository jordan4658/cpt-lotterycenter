package com.caipiao.core.library.model.file.vo;


import java.util.List;

/**
 * 上传文件VO
 */
public class PapersVO {

    /**
     * 文件访问主机
     */
    private String httpHostPapers;

    /**
     * 已上传文件URI
     */
    private List<String> papersUri;

    public String getHttpHostPapers() {
        return httpHostPapers;
    }

    public void setHttpHostPapers(String httpHostPapers) {
        this.httpHostPapers = httpHostPapers;
    }

    public List<String> getPapersUri() {
        return papersUri;
    }

    public void setPapersUri(List<String> papersUri) {
        this.papersUri = papersUri;
    }
}
