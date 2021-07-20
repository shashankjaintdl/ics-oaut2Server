package com.ics.oauth2server.helper;


public class EnumsExtension {

    public static enum eUserType {
        enAD("AD", "Admin"), enSUP("SUP", "Supplier"), enCUS("CUS", "Customer"), enVEN("VEN", "Vender"), enDEV_EMP("DEV_EMP", "deveoperEmployee"),
        enDEV("DEV", "developer"), enDEL("DEL", "delivery"), enEMP_A("EMP_A", "employee"), enEMP_C("EMP_C", "employee"), enEMP_CAT("EMP_CAT", "employee"),
        enEMP_R("EMP_R", "employee"), enSDEV("SDEV", "SuperDeveloper"), enEMP("EMP", "employee");

        private String firstvalue;
        private String secondValue;

        eUserType(String firstvalue, String secondValue) {
            this.firstvalue = firstvalue;
            this.secondValue = secondValue;
        }

        public String getKey() {
            return this.firstvalue;
        }

        public String getValue() {
            return this.secondValue;
        }

        public static String getFirstValue(String value) {
            for (eUserType item : eUserType.values()) {
                if (item.getValue().equalsIgnoreCase(value))
                    return item.getKey();
            }
            return "";
        }

        public static String getSecondValue(String value) {
            for (eUserType item : eUserType.values()) {
                if (item.getKey().equalsIgnoreCase(value))
                    return item.getValue();
            }
            return "";
        }
    }

    public static enum eOrderBy {
        enAsc("Asc", "Ascending Order"), enDesc("Desc", "Descending Order");

        private String firstvalue;
        private String secondValue;

        eOrderBy(String firstvalue, String secondValue) {
            this.firstvalue = firstvalue;
            this.secondValue = secondValue;
        }

        public String getKey() {
            return this.firstvalue;
        }

        public String getValue() {
            return this.secondValue;
        }

        public static String getFirstValue(String value) {
            for (eOrderBy item : eOrderBy.values()) {
                if (item.getValue().equalsIgnoreCase(value))
                    return item.getKey();
            }
            return "";
        }

        public static String getSecondValue(String value) {
            for (eOrderBy item : eOrderBy.values()) {
                if (item.getKey().equalsIgnoreCase(value))
                    return item.getValue();
            }
            return "";
        }
    }

}

