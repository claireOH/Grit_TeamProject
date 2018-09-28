package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DB_web {
    
    URL                         url;
    HttpURLConnection           con;
    
    JSONParser                  jsonParser      = new JSONParser ();

    private String connect_get (String link) {
        
        try {
            /* -------------------------------------------------------- */

                        url                 = new URL(link);
                        con                 = (HttpURLConnection)url.openConnection();
            
            String      json_string         = "";

            /* -------------------------------------------------------- */

            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-length", "0");

            /* -------------------------------------------------------- */

            BufferedReader  buffer   = new BufferedReader(new InputStreamReader(
                    con.getInputStream()
            ));

            while (true) {
                String line = buffer.readLine();

                if (line == null) {
                    break;
                }

                json_string += line;
            }

            buffer.close();
            return json_string;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private String connect_post (String link, String param) {
        
        try {
            /* -------------------------------------------------------- */

                        url                 = new URL(link);
                        con                 = (HttpURLConnection)url.openConnection();
            
            String      json_string         = "";
            
            /* -------------------------------------------------------- */

            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);
            
            /* -------------------------------------------------------- */

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(param.getBytes());
            outputStream.flush();
            outputStream.close();

            /* -------------------------------------------------------- */

            BufferedReader  buffer   = new BufferedReader(new InputStreamReader(
                    con.getInputStream()
                    
            ));

            while (true) {
                String line = buffer.readLine();

                if (line == null) {
                    break;
                }

                json_string += line;
            }

            buffer.close();
            return json_string;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /* lgoin */
    public ArrayList<String> post_confirmResult (String id, String password) {

        String              link            = "http://13.124.213.132/check_student";
        String              param           = "id=" + id + "&password=" + password;

        String              json_string     = "";

        ArrayList<String>   result_array    = new ArrayList<>();

        /* -------------------------------------------------------- */

        /* check the returned value */
        json_string                     = this.connect_post(link, param);
        
        /* -------------------------------------------------------- */
        
        if (json_string != null) {
            /* json string parse */
            try {
                /* -------------------------------------------------------- */
                
                JSONObject      jsonObject  = (JSONObject)jsonParser.parse(json_string);

                /* -------------------------------------------------------- */
                
                Boolean         status      = (Boolean)jsonObject.get("status");

                /* -------------------------------------------------------- */

                if (status) {
                    JSONObject   message = (JSONObject)jsonObject.get("message");
                    
                    /* -------------------------------------------------------- */

                    result_array.add((String)message.get("id"));
                    result_array.add((String)message.get("name"));
                    result_array.add((String)message.get("photo"));
                    
                    /* -------------------------------------------------------- */

                    return result_array;

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    
    /* timetable */
    public LinkedHashMap<Integer, String> get_timeTable () {

        String              link            = "";
        String              json_string     = "";

        LinkedHashMap<Integer, String> result   = new LinkedHashMap<>();
        
        /* -------------------------------------------------------- */
        
        /* check the day of tday */
        Calendar            cal             = Calendar.getInstance();
        int                 day_of_week     = cal.get(Calendar.DAY_OF_WEEK) - 1;

        /* -------------------------------------------------------- */

        /* no lecture on saturday and sunday */
        if (day_of_week > 0 && day_of_week < 6) {
            /* set link */
            link        = "http://13.124.213.132/timetable?class_id=1&day_of_week=" + day_of_week;

            /* -------------------------------------------------------- */

            json_string                         = this.connect_get(link);
            
            /* -------------------------------------------------------- */
            
            /* DB connect */
            if (json_string          != null) {
                try {
                    JSONObject  jsonObject      = (JSONObject)jsonParser.parse(json_string);
                    
                    /* -------------------------------------------------------- */
                    
                    Boolean     status          = (Boolean)jsonObject.get("status");
                    
                    /* -------------------------------------------------------- */

                    /* if return value is true */
                    if (status) {
                        JSONArray   message     = (JSONArray)jsonObject.get("message");
                        
                        /* -------------------------------------------------------- */

                        for (int arrCount = 0 ; arrCount < message.size() ; arrCount++) {
                            JSONObject  obj     = (JSONObject)message.get(arrCount);
                            result.put(((Long)obj.get("period")).intValue(), (String)obj.get("subject_name"));
                        }

                        /* -------------------------------------------------------- */
                        
                        return result;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                /* ========================================================== */
            }
        }

        return null;
    }
    
    /* Attendance status */
    public ArrayList<HashMap<String, String>> get_attendanceStatus () {

        String                  link            = "http://13.124.213.132/attendance?class_id=1";
        String                  json_string     = "";

        HashMap<String, String> absence_map     = new LinkedHashMap<String, String>();
        HashMap<String, String> attend_map      = new LinkedHashMap<String, String>();
        HashMap<String, String> late_map        = new LinkedHashMap<String, String>();

        ArrayList<HashMap<String, String>>   result_map     = new ArrayList<HashMap<String, String>>();

        /* -------------------------------------------------------- */
        
        json_string = this.connect_get(link);
        
        /* -------------------------------------------------------- */

        /* json string parse */
        if (json_string != null) {
            try {
                
                JSONObject jsonObject = (JSONObject)jsonParser.parse(json_string);
                
                /* -------------------------------------------------------- */

                Boolean status = (Boolean)jsonObject.get("status");
                
                /* -------------------------------------------------------- */

                if (status) {
                    JSONObject   message = (JSONObject)jsonObject.get("message");
                    
                    /* -------------------------------------------------------- */

                    JSONArray   absenceArray    = (JSONArray)message.get("absence");
                    JSONArray   sign_inArray    = (JSONArray)message.get("sign_in");
                    JSONArray   latenessArray   = (JSONArray)message.get("lateness");
                    
                    /* -------------------------------------------------------- */

                    for (int count = 0 ; count < absenceArray.size() ; count++) {
                        JSONObject obj = (JSONObject)absenceArray.get(count);
                        absence_map.put(((String)obj.get("id")), (String)obj.get("name"));
                    }

                    for (int count = 0 ; count < sign_inArray.size() ; count++) {
                        JSONObject obj = (JSONObject)sign_inArray.get(count);
                        attend_map.put(((String)obj.get("id")), (String)obj.get("name"));
                    }

                    for (int count = 0 ; count < latenessArray.size() ; count++) {
                        JSONObject obj = (JSONObject)latenessArray.get(count);
                        late_map.put(((String)obj.get("id")), (String)obj.get("name"));
                    }
                    
                    /* -------------------------------------------------------- */

                    result_map.add(absence_map);
                    result_map.add(attend_map);
                    result_map.add(late_map);
                    
                    /* -------------------------------------------------------- */

                    return result_map;

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        /* -------------------------------------------------------- */

        return null;
    }
    
    /* check attnd */
    public ArrayList<String> post_attendance (String action, String id) {
        
        String              link            = "http://13.124.213.132/student/" + action;
        String              param           = "student_number_id=" + id + "&detail=good";

        String              json_string     = "";
        
        ArrayList<String>   result_array    = new ArrayList<>();

        /* -------------------------------------------------------- */
        
        /* check the returned value */
        json_string = this.connect_post(link, param);
        
        /* -------------------------------------------------------- */
        
        if (json_string != null) {
            /* json string parse */
            try {
                JSONObject      jsonObject  = (JSONObject)jsonParser.parse(json_string);
                
                /* -------------------------------------------------------- */
                
                Boolean         status      = (Boolean)jsonObject.get("status");
                
                /* -------------------------------------------------------- */

                if (status) {
                    JSONObject   message = (JSONObject)jsonObject.get("message");
                    
                    /* -------------------------------------------------------- */

                    result_array.add((String)message.get("id"));
                    result_array.add((String)message.get("name"));
                    result_array.add((String)message.get("photo"));
                    
                    /* -------------------------------------------------------- */

                } else {
                    result_array.add((String)jsonObject.get("message"));
                }

                return result_array;
                
            } catch (ParseException e) {
                e.printStackTrace();
            }
        
        }
        
        return null;
    
    }
}
