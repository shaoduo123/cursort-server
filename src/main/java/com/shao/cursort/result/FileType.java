package com.shao.cursort.result;

public enum FileType {
    PNG(".png","PHOTO") ,
    BMP(".bmp","PHOTO") ,
    GIF(".gif","PHOTO"),
    TIFF(".tiff","PHOTO"),
    JPEG(".jpeg","PHOTO") ,
    JPG(".jpg","PHOTO") ,
    AVI(".avi","VIDEO"),
    RMVB(".rmvb","VIDEO"),
    FLV(".flv","VIDEO"),
    MP4(".mp4","VIDEO"),
    _3GP(".3gp","VIDEO"),
    MOV(".mov","VIDEO"),
    UNKNOWN(".unknown","UNKNOWN");

    private String suffixes ;
    private String type ;

    FileType(String suffixes, String type) {
        this.suffixes = suffixes ;
        this.type = type ;
    }

    public String getSuffixes() {
        return suffixes;
    }

    public String getType() {
        return type;
    }

    public static String pickType(String suffixes) {
        for (FileType f: FileType.values()
             ) {
            if(suffixes.equals(f.getSuffixes())) {
                return f.getType() ;
            }
        }

        return UNKNOWN.getType();
    }


}
