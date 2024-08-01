package com.andyprofinnovations.model;

public class Approval {
   private int Aid;
   private String name;


   public Approval() {
   }

   public Approval(int aid, String name) {
      Aid = aid;
      this.name = name;
   }

   public int getAid() {
      return Aid;
   }

   public void setAid(int aid) {
      Aid = aid;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}

