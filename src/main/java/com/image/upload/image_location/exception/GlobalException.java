package com.image.upload.image_location.exception;


import com.image.upload.image_location.Result.CodeMsg;

public class GlobalException extends RuntimeException{

        private static final long serivalVersionUID = 1L;

        private CodeMsg cm;

    public static long getSerivalVersionUID() {
        return serivalVersionUID;
    }

    public CodeMsg getCm() {
        return cm;
    }

    public void setCm(CodeMsg cm) {
        this.cm = cm;
    }

    public GlobalException(CodeMsg cm){
            super(cm.toString());
            this.cm = cm;
        }
}
