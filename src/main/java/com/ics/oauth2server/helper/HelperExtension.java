package com.ics.oauth2server.helper;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class HelperExtension {

    long Time = 0;

    public String getUniqueId() {
        return (new Date().getTime() + RandonNumber() + "");
    }

    public Timestamp getDateTime() {
        long serverTimeStamp = new Date().getTime() + Time;
        return new Timestamp(serverTimeStamp);
    }

    public Timestamp getDateTime(String string_timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(string_timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Timestamp(date.getTime());
    }

    public Date getDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date sqlDate = null;
        try {
            Date utilDate = format.parse(date);
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sqlDate;
    }

    public int RandonNumber() {
        int randomNumber = (new Random().nextInt());
        return randomNumber;
    }

    public Date timestampToDate(long time) {
        if (time == 0) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getDefault());
        return new Date(time);
    }

    public Date timestampToDatePlusOneDay(long time) {
        time += 86400000L;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getDefault());
        return new Date(time);
    }

    public String millisToDate(long time) {
        // Input -> millis ->1513408806000
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(time);
        return cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        // Output -> Date ->16-01-2017
    }

    public String millisToICGDate(long time) {
        // Input -> millis ->1513408806000
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(time);
        String date = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.DAY_OF_MONTH) + (cal.get(Calendar.MONTH) + 1) + "_"
                + cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        String minuteString = "";
        if (minute / 10 == 0) {
            minuteString = "0" + minute;
        } else {
            minuteString = String.valueOf(minute);
        }
        return date + minuteString;
        // Output -> Date ->20171206_2104
    }

    public String millisToDatePostcard(long time) {
        // Input -> millis ->1513408806000
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(time);
        return cal.get(Calendar.DAY_OF_MONTH) + " " + getMonthStringAbbrivated(cal.get(Calendar.MONTH) + 1) + " "
                + cal.get(Calendar.YEAR);
        // Output -> Date ->16 Jan 2017
    }

    public String millisToDateTime(long millis) {
        HelperExtension.Print("millis ->" + millis);
        // Input -> millis ->1513408806000
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(millis);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
        String datetime = sdf.format(cal.getTime());
        HelperExtension.Print("Date ->" + datetime);
        // Output -> Date ->16-12-2017 12:50:06 PM
        return datetime;
    }

    public static void Print(Object message) {
        if (true) {
            System.out.println(message);
        }
    }

    public boolean isNullOrEmpty(Object message) {
        if (message != null) {
            if (!message.toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public String isNullString(String message) {
        if (message == null || message.equalsIgnoreCase("null")) {
            return "";
        }
        return message.toString();
    }

    public String isNullInt(String message) {
        if (message == null) {
            return "";
        }
        return message.toString();
    }

    public String getMonthStringAbbrivated(int month) {
        String monthString;
        switch (month) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            case 12:
                monthString = "Dec";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        return monthString;
    }

    public String getMonthInt(String month) {
        String monthInt;
        switch (month.toUpperCase()) {
            case "JAN":
                monthInt = "01";
                break;
            case "FEB":
                monthInt = "02";
                break;
            case "MAR":
                monthInt = "03";
                break;
            case "APR":
                monthInt = "04";
                break;
            case "MAY":
                monthInt = "05";
                break;
            case "JUN":
                monthInt = "06";
                break;
            case "JUL":
                monthInt = "07";
                break;
            case "AUG":
                monthInt = "08";
                break;
            case "SEP":
                monthInt = "09";
                break;
            case "OCT":
                monthInt = "10";
                break;
            case "NOV":
                monthInt = "11";
                break;
            case "DEC":
                monthInt = "12";
                break;
            default:
                monthInt = "01";
                break;
        }
        return monthInt;
    }

    public String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);

    }

    public Integer[] pagination(DatabaseHelper databaseHelper, int items) {
        int itemPerPage = databaseHelper.getItemPerPage();
        int cp = databaseHelper.getCurrentPage();
        int npg = (int) Math.ceil(Double.parseDouble("" + items / itemPerPage + "." + items % itemPerPage));
        Integer count[] = null;
        int x = 0;
        if (npg <= 5) {
            count = new Integer[npg];
            for (int i = 1; i <= npg; i++) {
                count[x] = i;
                x++;
            }
            return count;
        }
        if (npg > 5) {
            count = new Integer[5];
            if (cp == npg - 2) {
                for (int i = cp - 2; i <= npg; i++) {
                    count[x] = i;
                    x++;
                }
            }
            if (cp > npg - 2) {
                for (int i = npg - 4; i <= npg; i++) {
                    count[x] = i;
                    x++;
                }
            }
            if (cp <= 3) {
                for (int i = 1; i <= 5; i++) {
                    count[x] = i;
                    x++;
                }
            }
            if (cp < npg - 2 && cp > 3) {
                for (int i = cp - 2; i <= cp + 2; i++) {
                    count[x] = i;
                    x++;
                }
            }
            return count;
        }
        return count;
    }

    // Start Convert long to date
    public Date startingDate(long startDate) {
        Date date = new Date(startDate);
        Date satrtingDate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String start = df.format(date) + " 00:00:01";
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            satrtingDate = inputFormatter.parse(start);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return satrtingDate;
    }

    public Date endingDate(long endDate) {
        Date date = new Date(endDate);
        Date satrtingDate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String end = df.format(date) + " 23:59:59";
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            satrtingDate = inputFormatter.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return satrtingDate;
    }
    // End convert long to date

    public boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Date addDays(Date date, Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public int noOfDaysFromDateToToDate(Date fromDate, Date toDate) {
        LocalDate fromDateLocal = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate toDateLocal = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period p = Period.between(fromDateLocal, toDateLocal);
        return p.getDays();
    }

    public String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}

