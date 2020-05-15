package com.ebs.rental.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTransportDetailsDescOjbect {

    private static final String TAG_EQPDESC = "eqpdesc";
    private static final String TAG_EQPDESC01 = "eqpdesc01";
    private static final String TAG_EQPDESC02 = "eqpdesc02";
    private static final String TAG_EQPDESC03 = "eqpdesc03";
    private static final String TAG_EQPDESC04 = "eqpdesc04";
    private static final String TAG_EQPDESC05 = "eqpdesc05";
    private static final String TAG_EQPDESC06 = "eqpdesc06";
    private static final String TAG_EQPDESC07 = "eqpdesc07";
    private static final String TAG_EQPDESC08 = "eqpdesc08";
    private static final String TAG_EQPDESC09 = "eqpdesc09";

    private static final String TAG_KEQUIP_NUM = "kequipnum";
    private static final String TAG_KEQUIP_NUM01 = "kequipnu01";
    private static final String TAG_KEQUIP_NUM02 = "kequipnu02";
    private static final String TAG_KEQUIP_NUM03 = "kequipnu03";
    private static final String TAG_KEQUIP_NUM04 = "kequipnu04";
    private static final String TAG_KEQUIP_NUM05 = "kequipnu05";
    private static final String TAG_KEQUIP_NUM06 = "kequipnu06";
    private static final String TAG_KEQUIP_NUM07 = "kequipnu07";
    private static final String TAG_KEQUIP_NUM08 = "kequipnu08";
    private static final String TAG_KEQUIP_NUM09 = "kequipnu09";

    private static final String TAG_KMFG = "kmfg";
    private static final String TAG_KMFG01 = "kmfg01";
    private static final String TAG_KMFG02 = "kmfg02";
    private static final String TAG_KMFG03 = "kmfg03";
    private static final String TAG_KMFG04 = "kmfg04";
    private static final String TAG_KMFG05 = "kmfg05";
    private static final String TAG_KMFG06 = "kmfg06";
    private static final String TAG_KMFG07 = "kmfg07";
    private static final String TAG_KMFG08 = "kmfg08";
    private static final String TAG_KMFG09 = "kmfg09";

    private static final String TAG_KMODEL = "kmodel";
    private static final String TAG_KMODEL01 = "kmodel01";
    private static final String TAG_KMODEL02 = "kmodel02";
    private static final String TAG_KMODEL03 = "kmodel03";
    private static final String TAG_KMODEL04 = "kmodel04";
    private static final String TAG_KMODEL05 = "kmodel05";
    private static final String TAG_KMODEL06 = "kmodel06";
    private static final String TAG_KMODEL07 = "kmodel07";
    private static final String TAG_KMODEL08 = "kmodel08";
    private static final String TAG_KMODEL09 = "kmodel09";

    private static final String TAG_K_SERIAL_NUM = "kserialno1";
    private static final String TAG_K_SERIAL_NUM_01 = "kserialn01";
    private static final String TAG_K_SERIAL_NUM_02 = "kserialn02";
    private static final String TAG_K_SERIAL_NUM_03 = "kserialn03";
    private static final String TAG_K_SERIAL_NUM_04 = "kserialn04";
    private static final String TAG_K_SERIAL_NUM_05 = "kserialn05";
    private static final String TAG_K_SERIAL_NUM_06 = "kserialn06";
    private static final String TAG_K_SERIAL_NUM_07 = "kserialn07";
    private static final String TAG_K_SERIAL_NUM_08 = "kserialn08";
    private static final String TAG_K_SERIAL_NUM_09 = "kserialn09";

    private static final String TAG_MESSAGE = "message";

    private String eqpdesc1;
    private String eqpdesc01;
    private String eqpdesc02;
    private String eqpdesc03;
    private String eqpdesc04;
    private String eqpdesc05;
    private String eqpdesc06;
    private String eqpdesc07;
    private String eqpdesc08;
    private String eqpdesc09;

    private String kequipnu1;
    private String kequipnu01;
    private String kequipnu02;
    private String kequipnu03;
    private String kequipnu04;
    private String kequipnu05;
    private String kequipnu06;
    private String kequipnu07;
    private String kequipnu08;
    private String kequipnu09;

    private String kmfg1;
    private String kmfg01;
    private String kmfg02;
    private String kmfg03;
    private String kmfg04;
    private String kmfg05;
    private String kmfg06;
    private String kmfg07;
    private String kmfg08;
    private String kmfg09;

    private String kmodel1;
    private String kmodel01;
    private String kmodel02;
    private String kmodel03;
    private String kmodel04;
    private String kmodel05;
    private String kmodel06;
    private String kmodel07;
    private String kmodel08;
    private String kmodel09;

    private String kserialn1;
    private String kserialn01;
    private String kserialn02;
    private String kserialn03;
    private String kserialn04;
    private String kserialn05;
    private String kserialn06;
    private String kserialn07;
    private String kserialn08;
    private String kserialn09;

    private String message;


    private ArrayList<String> eqpdesc = new ArrayList<>();
    private ArrayList<String> kequipnu= new ArrayList<>();
    private ArrayList<String> kmfg= new ArrayList<>();
    private ArrayList<String> kmodel= new ArrayList<>();
    private ArrayList<String> kserialn= new ArrayList<>();



    public String getEqpdesc01() {
        return eqpdesc01;
    }

    public void setEqpdesc01(String eqpdesc01) {
        this.eqpdesc01 = eqpdesc01;
    }

    public String getEqpdesc02() {
        return eqpdesc02;
    }

    public void setEqpdesc02(String eqpdesc02) {
        this.eqpdesc02 = eqpdesc02;
    }

    public String getEqpdesc03() {
        return eqpdesc03;
    }

    public void setEqpdesc03(String eqpdesc03) {
        this.eqpdesc03 = eqpdesc03;
    }

    public String getEqpdesc04() {
        return eqpdesc04;
    }

    public void setEqpdesc04(String eqpdesc04) {
        this.eqpdesc04 = eqpdesc04;
    }

    public String getEqpdesc05() {
        return eqpdesc05;
    }

    public void setEqpdesc05(String eqpdesc05) {
        this.eqpdesc05 = eqpdesc05;
    }

    public String getEqpdesc06() {
        return eqpdesc06;
    }

    public void setEqpdesc06(String eqpdesc06) {
        this.eqpdesc06 = eqpdesc06;
    }

    public String getEqpdesc07() {
        return eqpdesc07;
    }

    public void setEqpdesc07(String eqpdesc07) {
        this.eqpdesc07 = eqpdesc07;
    }

    public String getEqpdesc08() {
        return eqpdesc08;
    }

    public void setEqpdesc08(String eqpdesc08) {
        this.eqpdesc08 = eqpdesc08;
    }

    public String getEqpdesc09() {
        return eqpdesc09;
    }

    public void setEqpdesc09(String eqpdesc09) {
        this.eqpdesc09 = eqpdesc09;
    }

    public String getKequipnu01() {
        return kequipnu01;
    }

    public void setKequipnu01(String kequipnu01) {
        this.kequipnu01 = kequipnu01;
    }

    public String getKequipnu02() {
        return kequipnu02;
    }

    public void setKequipnu02(String kequipnu02) {
        this.kequipnu02 = kequipnu02;
    }

    public String getKequipnu03() {
        return kequipnu03;
    }

    public void setKequipnu03(String kequipnu03) {
        this.kequipnu03 = kequipnu03;
    }

    public String getKequipnu04() {
        return kequipnu04;
    }

    public void setKequipnu04(String kequipnu04) {
        this.kequipnu04 = kequipnu04;
    }

    public String getKequipnu05() {
        return kequipnu05;
    }

    public void setKequipnu05(String kequipnu05) {
        this.kequipnu05 = kequipnu05;
    }

    public String getKequipnu06() {
        return kequipnu06;
    }

    public void setKequipnu06(String kequipnu06) {
        this.kequipnu06 = kequipnu06;
    }

    public String getKequipnu07() {
        return kequipnu07;
    }

    public void setKequipnu07(String kequipnu07) {
        this.kequipnu07 = kequipnu07;
    }

    public String getKequipnu08() {
        return kequipnu08;
    }

    public void setKequipnu08(String kequipnu08) {
        this.kequipnu08 = kequipnu08;
    }

    public String getKequipnu09() {
        return kequipnu09;
    }

    public void setKequipnu09(String kequipnu09) {
        this.kequipnu09 = kequipnu09;
    }

    public String getKmfg01() {
        return kmfg01;
    }

    public void setKmfg01(String kmfg01) {
        this.kmfg01 = kmfg01;
    }

    public String getKmfg02() {
        return kmfg02;
    }

    public void setKmfg02(String kmfg02) {
        this.kmfg02 = kmfg02;
    }

    public String getKmfg03() {
        return kmfg03;
    }

    public void setKmfg03(String kmfg03) {
        this.kmfg03 = kmfg03;
    }

    public String getKmfg04() {
        return kmfg04;
    }

    public void setKmfg04(String kmfg04) {
        this.kmfg04 = kmfg04;
    }

    public String getKmfg05() {
        return kmfg05;
    }

    public void setKmfg05(String kmfg05) {
        this.kmfg05 = kmfg05;
    }

    public String getKmfg06() {
        return kmfg06;
    }

    public void setKmfg06(String kmfg06) {
        this.kmfg06 = kmfg06;
    }

    public String getKmfg07() {
        return kmfg07;
    }

    public void setKmfg07(String kmfg07) {
        this.kmfg07 = kmfg07;
    }

    public String getKmfg08() {
        return kmfg08;
    }

    public void setKmfg08(String kmfg08) {
        this.kmfg08 = kmfg08;
    }

    public String getKmfg09() {
        return kmfg09;
    }

    public void setKmfg09(String kmfg09) {
        this.kmfg09 = kmfg09;
    }

    public String getKmodel01() {
        return kmodel01;
    }

    public void setKmodel01(String kmodel01) {
        this.kmodel01 = kmodel01;
    }

    public String getKmodel02() {
        return kmodel02;
    }

    public void setKmodel02(String kmodel02) {
        this.kmodel02 = kmodel02;
    }

    public String getKmodel03() {
        return kmodel03;
    }

    public void setKmodel03(String kmodel03) {
        this.kmodel03 = kmodel03;
    }

    public String getKmodel04() {
        return kmodel04;
    }

    public void setKmodel04(String kmodel04) {
        this.kmodel04 = kmodel04;
    }

    public String getKmodel05() {
        return kmodel05;
    }

    public void setKmodel05(String kmodel05) {
        this.kmodel05 = kmodel05;
    }

    public String getKmodel06() {
        return kmodel06;
    }

    public void setKmodel06(String kmodel06) {
        this.kmodel06 = kmodel06;
    }

    public String getKmodel07() {
        return kmodel07;
    }

    public void setKmodel07(String kmodel07) {
        this.kmodel07 = kmodel07;
    }

    public String getKmodel08() {
        return kmodel08;
    }

    public void setKmodel08(String kmodel08) {
        this.kmodel08 = kmodel08;
    }

    public String getKmodel09() {
        return kmodel09;
    }

    public void setKmodel09(String kmodel09) {
        this.kmodel09 = kmodel09;
    }

    public String getKserialn01() {
        return kserialn01;
    }

    public String getKserialn02() {
        return kserialn02;
    }

    public String getKserialn03() {
        return kserialn03;
    }

    public String getKserialn04() {
        return kserialn04;
    }

    public String getKserialn05() {
        return kserialn05;
    }

    public String getKserialn06() {
        return kserialn06;
    }

    public String getKserialn07() {
        return kserialn07;
    }

    public String getKserialn08() {
        return kserialn08;
    }

    public String getKserialn09() {
        return kserialn09;
    }

    public void setKserialn01(String kserialn01) {
        this.kserialn01 = kserialn01;
    }

    public void setKserialn02(String kserialn02) {
        this.kserialn02 = kserialn02;
    }

    public void setKserialn03(String kserialn03) {
        this.kserialn03 = kserialn03;
    }

    public void setKserialn04(String kserialn04) {
        this.kserialn04 = kserialn04;
    }

    public void setKserialn05(String kserialn05) {
        this.kserialn05 = kserialn05;
    }

    public void setKserialn06(String kserialn06) {
        this.kserialn06 = kserialn06;
    }

    public void setKserialn07(String kserialn07) {
        this.kserialn07 = kserialn07;
    }

    public void setKserialn08(String kserialn08) {
        this.kserialn08 = kserialn08;
    }

    public void setKserialn09(String kserialn09) {
        this.kserialn09 = kserialn09;
    }

    public static ArrayList<GetTransportDetailsDescOjbect> parseData(String response)
            throws Exception {

        ArrayList<GetTransportDetailsDescOjbect> transportdesclist = new ArrayList<>();

        GetTransportDetailsDescOjbect transport = new GetTransportDetailsDescOjbect();

        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        for (int i = 0; i < detailarray.length(); i++) {

            transport = new GetTransportDetailsDescOjbect();
            JSONObject userDetails = detailarray.getJSONObject(i);


            transport.setEqpdesc1(userDetails.getString(TAG_EQPDESC));
            if(userDetails.getString(TAG_KEQUIP_NUM).length()!=0){
                if(userDetails.getString(TAG_KEQUIP_NUM).contains("_0")) {
                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC));
                }
            }

            transport.setEqpdesc01(userDetails.getString(TAG_EQPDESC01));
            if(userDetails.getString(TAG_KEQUIP_NUM01).length()!=0){
                if(userDetails.getString(TAG_KEQUIP_NUM01).contains("_0")){
                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC01));
                }

            }

            transport.setEqpdesc02(userDetails.getString(TAG_EQPDESC02));
            if(userDetails.getString(TAG_KEQUIP_NUM02).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM02).contains("_0")) {
                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC02));
                }
            }

            transport.setEqpdesc03(userDetails.getString(TAG_EQPDESC03));

            if(userDetails.getString(TAG_KEQUIP_NUM03).length()!=0) {

                if(userDetails.getString(TAG_KEQUIP_NUM03).contains("_0")) {

                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC03));
                }
            }


            transport.setEqpdesc04(userDetails.getString(TAG_EQPDESC04));
            if(userDetails.getString(TAG_KEQUIP_NUM04).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM04).contains("_0")) {
                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC04));
                }
            }
            transport.setEqpdesc05(userDetails.getString(TAG_EQPDESC05));
            if(userDetails.getString(TAG_KEQUIP_NUM05).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM05).contains("_0")) {
                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC05));
                }
            }
            transport.setEqpdesc06(userDetails.getString(TAG_EQPDESC06));
            if(userDetails.getString(TAG_KEQUIP_NUM06).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM06).contains("_0")) {
                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC06));
                }
            }
            transport.setEqpdesc07(userDetails.getString(TAG_EQPDESC07));
            if(userDetails.getString(TAG_KEQUIP_NUM07).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM07).contains("_0")) {

                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC07));
                }
            }
            transport.setEqpdesc08(userDetails.getString(TAG_EQPDESC08));
            if(userDetails.getString(TAG_KEQUIP_NUM08).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM08).contains("_0")) {
                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC08));
                }
            }

            transport.setEqpdesc09(userDetails.getString(TAG_EQPDESC09));
            if(userDetails.getString(TAG_KEQUIP_NUM09).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM09).contains("_0")) {
                    transport.getEqpdesc().add(userDetails.getString(TAG_EQPDESC09));
                }
            }
            
            transport.setKequipnu1(userDetails.getString(TAG_KEQUIP_NUM));
            if(userDetails.getString(TAG_KEQUIP_NUM).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }

            transport.setKequipnu01(userDetails.getString(TAG_KEQUIP_NUM01));
            if(userDetails.getString(TAG_KEQUIP_NUM01).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM01).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM01).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }
            transport.setKequipnu02(userDetails.getString(TAG_KEQUIP_NUM02));
            if(userDetails.getString(TAG_KEQUIP_NUM02).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM02).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM02).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }
            transport.setKequipnu03(userDetails.getString(TAG_KEQUIP_NUM03));
            if(userDetails.getString(TAG_KEQUIP_NUM03).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM03).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM03).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }
            transport.setKequipnu04(userDetails.getString(TAG_KEQUIP_NUM04));
            if(userDetails.getString(TAG_KEQUIP_NUM04).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM04).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM04).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }
            transport.setKequipnu05(userDetails.getString(TAG_KEQUIP_NUM05));
            if(userDetails.getString(TAG_KEQUIP_NUM05).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM05).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM05).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }
            transport.setKequipnu06(userDetails.getString(TAG_KEQUIP_NUM06));
            if(userDetails.getString(TAG_KEQUIP_NUM06).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM06).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM06).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }
            transport.setKequipnu07(userDetails.getString(TAG_KEQUIP_NUM07));
            if(userDetails.getString(TAG_KEQUIP_NUM07).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM07).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM07).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }
            transport.setKequipnu08(userDetails.getString(TAG_KEQUIP_NUM08));
            if(userDetails.getString(TAG_KEQUIP_NUM08).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM08).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM08).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }
            transport.setKequipnu09(userDetails.getString(TAG_KEQUIP_NUM09));
            if(userDetails.getString(TAG_KEQUIP_NUM09).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM09).contains("_0")) {
                    String str = userDetails.getString(TAG_KEQUIP_NUM09).replace("_0","");
                    transport.getKequipnu().add(str);
                }
            }



            transport.setKmfg1(userDetails.getString(TAG_KMFG));
            if(userDetails.getString(TAG_KEQUIP_NUM).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM).contains("_0")) {
                    transport.getKmfg().add(userDetails.getString(TAG_KMFG));
                }
            }
            transport.setKmfg01(userDetails.getString(TAG_KMFG01));
            if(userDetails.getString(TAG_KEQUIP_NUM01).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM01).contains("_0")) {

                    transport.getKmfg().add(userDetails.getString(TAG_KMFG01));
                }
            }
            transport.setKmfg02(userDetails.getString(TAG_KMFG02));
            if(userDetails.getString(TAG_KEQUIP_NUM02).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM02).contains("_0")) {
                    transport.getKmfg().add(userDetails.getString(TAG_KMFG02));
                }
            }
            transport.setKmfg03(userDetails.getString(TAG_KMFG03));
            if(userDetails.getString(TAG_KEQUIP_NUM03).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM03).contains("_0")) {
                    transport.getKmfg().add(userDetails.getString(TAG_KMFG03));
                }
            }
            transport.setKmfg04(userDetails.getString(TAG_KMFG04));
            if(userDetails.getString(TAG_KEQUIP_NUM04).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM04).contains("_0")) {
                    transport.getKmfg().add(userDetails.getString(TAG_KMFG04));
                }
            }
            transport.setKmfg05(userDetails.getString(TAG_KMFG05));
            if(userDetails.getString(TAG_KEQUIP_NUM05).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM05).contains("_0")) {
                    transport.getKmfg().add(userDetails.getString(TAG_KMFG05));
                }
            }
            transport.setKmfg06(userDetails.getString(TAG_KMFG06));
            if(userDetails.getString(TAG_KEQUIP_NUM06).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM06).contains("_0")) {
                    transport.getKmfg().add(userDetails.getString(TAG_KMFG06));
                }
            }
            transport.setKmfg07(userDetails.getString(TAG_KMFG07));
            if(userDetails.getString(TAG_KEQUIP_NUM07).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM07).contains("_0")) {
                    transport.getKmfg().add(userDetails.getString(TAG_KMFG07));
                }
            }
            transport.setKmfg08(userDetails.getString(TAG_KMFG08));
            if(userDetails.getString(TAG_KEQUIP_NUM08).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM08).contains("_0")) {
                    transport.getKmfg().add(userDetails.getString(TAG_KMFG08));
                }
            }
            transport.setKmfg09(userDetails.getString(TAG_KMFG09));
            if(userDetails.getString(TAG_KEQUIP_NUM09).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM09).contains("_0")) {
                    transport.getKmfg().add(userDetails.getString(TAG_KMFG09));
                }
            }

            transport.setKmodel1(userDetails.getString(TAG_KMODEL));
            if(userDetails.getString(TAG_KEQUIP_NUM).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM).contains("_0")) {

                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL));
                }
            }
            transport.setKmodel01(userDetails.getString(TAG_KMODEL01));
            if(userDetails.getString(TAG_KEQUIP_NUM01).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM01).contains("_0")) {
                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL01));
                }
            }
            transport.setKmodel02(userDetails.getString(TAG_KMODEL02));
            if(userDetails.getString(TAG_KEQUIP_NUM02).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM02).contains("_0")) {
                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL02));
                }
            }
            transport.setKmodel03(userDetails.getString(TAG_KMODEL03));
            if(userDetails.getString(TAG_KEQUIP_NUM03).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM03).contains("_0")) {
                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL03));
                }
            }
            transport.setKmodel04(userDetails.getString(TAG_KMODEL04));
            if(userDetails.getString(TAG_KEQUIP_NUM04).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM04).contains("_0")) {
                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL04));
                }
            }
            transport.setKmodel05(userDetails.getString(TAG_KMODEL05));
            if(userDetails.getString(TAG_KEQUIP_NUM05).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM05).contains("_0")) {
                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL05));
                }
            }
            transport.setKmodel06(userDetails.getString(TAG_KMODEL06));
            if(userDetails.getString(TAG_KEQUIP_NUM06).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM06).contains("_0")) {
                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL06));
                }
            }
            transport.setKmodel07(userDetails.getString(TAG_KMODEL07));
            if(userDetails.getString(TAG_KEQUIP_NUM07).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM07).contains("_0")) {
                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL07));
                }
            }
            transport.setKmodel08(userDetails.getString(TAG_KMODEL08));
            if(userDetails.getString(TAG_KEQUIP_NUM08).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM08).contains("_0")) {
                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL08));
                }
            }
            transport.setKmodel09(userDetails.getString(TAG_KMODEL09));
            if(userDetails.getString(TAG_KEQUIP_NUM09).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM09).contains("_0")) {
                    transport.getKmodel().add(userDetails.getString(TAG_KMODEL09));
                }
            }

            transport.setKserialn1(userDetails.getString(TAG_K_SERIAL_NUM));
            if(userDetails.getString(TAG_KEQUIP_NUM).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM));
                }
            }
            transport.setKserialn01(userDetails.getString(TAG_K_SERIAL_NUM_01));
            if(userDetails.getString(TAG_KEQUIP_NUM01).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM01).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM_01));
                }
            }
            transport.setKserialn02(userDetails.getString(TAG_K_SERIAL_NUM_02));
            if(userDetails.getString(TAG_KEQUIP_NUM02).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM02).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM_02));
                }
            }
            transport.setKserialn03(userDetails.getString(TAG_K_SERIAL_NUM_03));
            if(userDetails.getString(TAG_KEQUIP_NUM03).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM03).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM_03));
                }
            }
            transport.setKserialn04(userDetails.getString(TAG_K_SERIAL_NUM_04));
            if(userDetails.getString(TAG_KEQUIP_NUM04).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM04).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM_04));
                }
            }
            transport.setKserialn05(userDetails.getString(TAG_K_SERIAL_NUM_05));
            if(userDetails.getString(TAG_KEQUIP_NUM05).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM05).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM_05));
                }
            }
            transport.setKserialn06(userDetails.getString(TAG_K_SERIAL_NUM_06));
            if(userDetails.getString(TAG_KEQUIP_NUM06).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM06).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM_06));
                }
            }
            transport.setKserialn07(userDetails.getString(TAG_K_SERIAL_NUM_07));
            if(userDetails.getString(TAG_KEQUIP_NUM07).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM07).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM_07));
                }
            }
            transport.setKserialn08(userDetails.getString(TAG_K_SERIAL_NUM_08));
            if(userDetails.getString(TAG_KEQUIP_NUM08).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM08).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM_08));
                }
            }
            transport.setKserialn09(userDetails.getString(TAG_K_SERIAL_NUM_09));
            if(userDetails.getString(TAG_KEQUIP_NUM09).length()!=0) {
                if(userDetails.getString(TAG_KEQUIP_NUM09).contains("_0")) {
                    transport.getKserialn().add(userDetails.getString(TAG_K_SERIAL_NUM_09));
                }
            }

            if (userDetails.getString(TAG_MESSAGE).length() != 0) {
                transport.setMessage(userDetails.getString(TAG_MESSAGE));
            }

            transportdesclist.add(transport);

        }

        return transportdesclist;

    }

    public ArrayList<String> getEqpdesc() {
        return eqpdesc;
    }

    public void setEqpdesc(ArrayList<String> eqpdesc) {
        this.eqpdesc = eqpdesc;
    }

    public ArrayList<String> getKequipnu() {
        return kequipnu;
    }

    public void setKequipnu(ArrayList<String> kequipnu) {
        this.kequipnu = kequipnu;
    }

    public ArrayList<String> getKmfg() {
        return kmfg;
    }

    public void setKmfg(ArrayList<String> kmfg) {
        this.kmfg = kmfg;
    }

    public ArrayList<String> getKmodel() {
        return kmodel;
    }

    public void setKmodel(ArrayList<String> kmodel) {
        this.kmodel = kmodel;
    }

    public ArrayList<String> getKserialn() {
        return kserialn;
    }

    public void setKserialn(ArrayList<String> kserialn) {
        this.kserialn = kserialn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEqpdesc1() {
        return eqpdesc1;
    }

    public void setEqpdesc1(String eqpdesc1) {
        this.eqpdesc1 = eqpdesc1;
    }

    public String getKequipnu1() {
        return kequipnu1;
    }

    public void setKequipnu1(String kequipnu1) {
        this.kequipnu1 = kequipnu1;
    }

    public String getKmfg1() {
        return kmfg1;
    }

    public void setKmfg1(String kmfg1) {
        this.kmfg1 = kmfg1;
    }

    public String getKmodel1() {
        return kmodel1;
    }

    public void setKmodel1(String kmodel1) {
        this.kmodel1 = kmodel1;
    }

    public String getKserialn1() {
        return kserialn1;
    }

    public void setKserialn1(String kserialn1) {
        this.kserialn1 = kserialn1;
    }
}
