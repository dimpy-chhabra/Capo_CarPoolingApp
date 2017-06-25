package com.example.dimpychhabra.capo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/*
*Project : CAPO, fully created by
* Dimpy Chhabra, IGDTUW, BTech, IT
* Second year (as of 2017)
* Expected Class of 2019
* Please do not circulate as your own
* Criticism is appreciated to work on memory leaks and bugs
* Contact Info : Find me on Linked in : linkedin.com/in/dimpy-chhabra
*
*/
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dimpy Chhabra on 3/25/2017.
 */

public class Ride {

    private String r_id;
    private String driver_id;
    private String start_loc;
    private String desti_loc;
    private String start_time  ;
    private String exp_desti_time  ;
    private String no_seats ;
    private String no_seats_occ  ;
    private String price;
    private String extras;
    private String dateORide;
    private String rider_id;
    private String rider_name;
    private String rider_college;
    private String rider_mobNo;
    private String rider_sex;

    private String pp;
    private String pp_time;
    private String pp_price;
    private String pp_id;
    private String status;

    //(pp, pp_time, pp_price, pp_id, college, r_id);
   // private Context context;
    public Ride(){
    }

    public Ride(String PP, String PP_time, String PP_price, String PP_id, String college, String rid) {
        pp = PP;
        pp_time = PP_time;
        pp_price = PP_price;
        pp_id = PP_id;
        desti_loc = college;
        r_id = rid;
    }

    //r_id, rider_id, _pp_id, college
    public Ride(String R_ID, String RIDER_ID, String PP_ID, String col) {
        r_id = R_ID;
        rider_id = RIDER_ID;
        pp_id = PP_ID;
        desti_loc = col;
    }


//    public Ride(String startLoc, String DestiLoc, String seats, String stTime, String DestiTime, String Price) {
//        start_loc = startLoc;
//        desti_loc = DestiLoc;
//        no_seats = seats;
//        start_time = stTime;
//        exp_desti_time = DestiTime;
//        price = Price;
//    }


    public Ride(String startLoc, String DestiLoc, String seats, String stTime, String DestiTime, String DateOride, String R_id) {
        start_loc = startLoc;
        desti_loc = DestiLoc;
        no_seats = seats;
        start_time = stTime;
        exp_desti_time = DestiTime;
        dateORide = DateOride;
        r_id = R_id;
    }

    public String getR_id( String mobNo){
        return r_id;
    }

    public String getStart_loc() {
        return start_loc;
    }

    public String getDesti_loc() {
        return desti_loc;
    }

    public String getRider_id() {
        return rider_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getExp_desti_time() {
        return exp_desti_time;
    }

    public String getNo_seats() {
        return no_seats;
    }

    public String getNo_seats_occ() {
        return no_seats_occ;
    }

    public String getExtras() {
        return extras;
    }

    public String getPrice() {
        return price;
    }

    public String getR_id() {
        return r_id;
    }

    public String getDateORide() {
        return dateORide;
    }

    public String getPp() {
        return pp;
    }

    public String getPp_time() {
        return pp_time;
    }

    public String getPp_price() {
        return pp_price;
    }

    public String getPp_id() {
        return pp_id;
    }

    public String getstatus() { return status;}




    public void putDriver_id( String mobNo){
        driver_id = mobNo;
    }
    public void putR_id( String R_ID){
        r_id = R_ID;
    }
    public void putStart_loc( String Start_LOC){
        start_loc = Start_LOC;
    }
    public void putDesti_loc( String Desti_lo){
        desti_loc = Desti_lo;
    }
    public void putStart_time( String Start_Time){
        start_time = Start_Time;
    }
    public void putExp_desti_time( String Exp_Desti_Time){
        exp_desti_time = Exp_Desti_Time;
    }
    public void putNo_seats( String No_Of_Seats){
        no_seats = No_Of_Seats;
    }
    public void putNo_seats_occ( String No_Seats_OCCUPIED){
        no_seats_occ = No_Seats_OCCUPIED;
    }
    public void putExtras( String Extras){
        extras = Extras;
    }
    public void putride_date ( String ride_date_1){
        dateORide = ride_date_1;
    }
    public void putpp (String pp1){
        pp = pp1;
    }
    public void putpptime( String pptime){
        pp_time= pptime;
    }
    public void putppId( String ppid){
        pp_id= ppid;
    }
    public void putStatus( String stat){
        status = stat;
    }
    public void putppPrice( String ppPrice){
        pp_price = ppPrice;
    }
    public void putRider_id(String riderId){ rider_id=riderId;}


    public String getRider_name() {
        return rider_name;
    }

    public void setRider_name(String rider_name) {
        this.rider_name = rider_name;
    }

    public String getRider_college() {
        return rider_college;
    }

    public void setRider_college(String rider_college) {
        this.rider_college = rider_college;
    }

    public String getRider_mobNo() {
        return rider_mobNo;
    }

    public void setRider_mobNo(String rider_mobNo) {
        this.rider_mobNo = rider_mobNo;
    }

    public String getRider_sex() {
        return rider_sex;
    }

    public void setRider_sex(String rider_sex) {
        this.rider_sex = rider_sex;
    }
}