package com.tys.entity.vo;

import java.io.Serializable;

public class Photo implements Serializable {
        String name;
        String url;
        Byte isQualified;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Byte getIsQualified() {
            return isQualified;
        }

        public void setIsQualified(Byte isQualified) {
            this.isQualified = isQualified;
        }
    }