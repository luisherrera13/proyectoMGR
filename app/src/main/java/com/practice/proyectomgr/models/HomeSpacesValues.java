package com.practice.proyectomgr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeSpacesValues {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("spaceName")
    @Expose
    private String spaceName;
    @SerializedName("spaceFunction")
    @Expose
    private String spaceFunction;
    @SerializedName("home_id")
    @Expose
    private String home_id;

   // private String name;
       // private String id;
       // private String spaceName;
        //private String spaceFunction;
        //private String home_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSpaceName() {
            return spaceName;
        }

        public void setSpaceName(String spaceName) {
            this.spaceName = spaceName;
        }

        public String getSpaceFunction() {
            return spaceFunction;
        }

        public void setSpaceFunction(String spaceFunction) {
            this.spaceFunction = spaceFunction;
        }

        public String getHome_id() {
            return home_id;
        }

        public void setHome_id(String home_id) {
            this.home_id = home_id;
        }

}
