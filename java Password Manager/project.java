import java.io.IOException;
import java.io.File;
import java.io.PrintWriter; // Print Writter class for Writing into files.
import java.util.Scanner;  // Scanner class to read from files & user input(on Terminal).
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class project{
    
    public static Scanner key = new Scanner(System.in);

    public static void main(String[] args){
        char option;
        boolean con = true;

        flush();

        while(con) {
            System.out.println(
            "  _____                                    _ \n" +
            " |  __ \\                                  | |\n" +
            " | |__) |_ _ ___ _____      _____  _ __ __| |\n" +
            " |  ___/ _` / __/ __\\ \\ /\\ / / _ \\| '__/ _` |\n" +
            " | |  | (_| \\__ \\__ \\\\ V  V / (_) | | | (_| |\n" +
            " |_|  _\\__,_|___/___/ \\_/\\_/ \\___/|_|  \\__,_|\n" +
            " |  \\/  |                                    \n" +
            " | \\  / | __ _ _ __   __ _  __ _  ___ _ __   \n" +
            " | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|  \n" +
            " | |  | | (_| | | | | (_| | (_| |  __/ |     \n" +
            " |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|     \n" +
            "                            __/ |            \n" +
            "                           |___/   \n");

            System.out.print("Please enter:\n1). Password manager\n2). Password Cehcking\n0). To exit.\n==> ");
            option = key.next().charAt(0);

            switch (option) {
                case '0':
                    flush();
                        con = false;
                        break;

                case '1':
                    flush();
                        option1();
                        break;

                case '2':
                    flush();
                        option2();
                        break;

                default:
                    flush();
                        System.out.println("WARNING: Enter a valid option!!!\n");
            }
        }
    }

    // Login and registeration functions.
    public static void option1(){
        boolean con = true;

        while (con){
            char option;
            System.out.print("Password manager\n\n1). Login.\n2). Registor.\n3). Delete Profile.\n0). Back\n==> ");
            option = key.next().charAt(0);

            switch(option) {
                case '0':
                flush();
                    con = false;
                    break;

                case '1':
                flush();
                    System.out.println("Login\n");
                    manager_login();
                flush();
                break;

                case '2':
                flush();
                    System.out.println("Registor\n");
                    manager_reg();
                    System.out.print("Press y to continue.\n==> ");
                    option = key.next().charAt(0);
                flush();
                break;

                case '3':
                flush();
                    System.out.println("Delete Profile\n");
                    manager_del();
                    System.out.print("Press y to continue.\n==> ");
                    option = key.next().charAt(0);
                flush();
                break;
                default:
                    flush();
                        System.out.println("WARNING: Enter a valid option!!!\n");
            }
        }
    }

    // Password manager. Login.
    public static void manager_login(){
        String username,password;
        boolean con = true;

        while (con) {
            System.out.print("Enter Username: ");
            username = key.next().toLowerCase();

            File handle = new File(".\\Profiles\\" + username + ".txt");
            if(handle.exists()){
                System.out.print("Enter Password: ");
                password = key.next();

                if (manager_authen(username, password)){
                    flush();
                    login_options(username);
                    con = false;
                }
                else {
                    flush();
                    System.out.println("Password Incorrect");
                }
            }
            else {
                flush();
                System.out.println("Username Not found");
            }
        }
    }

    // Password manager. Delete Profile.
    public static void manager_del(){
        boolean con = true;
        String username, password;
        char yn;

        while(con){
            System.out.print("Enter username: ");
            username = key.next().toLowerCase();
            File handle = new File(".\\Profiles\\" + username + ".txt");
            
            // Checking if file exists
            if(handle.exists()){
                System.out.print("Enter password: ");
                password = key.next();
                // authentication of password
                if(manager_authen(username, password)){
                    while(con){
                        System.out.print("\nDo to want to delete the account (y/n)\n==> ");
                        yn = key.next().toLowerCase().charAt(0);
                        switch (yn) {
                            case 'y':
                                if(handle.delete()){
                                    System.out.println("\nUsername \"" + username + "\" is Deleted!!!");
                                    con = false;
                                }
                                else{
                                    System.out.println("An Error Occoured");
                                }
                                break;
                            case 'n':
                                con = false;
                                break;
                            default:
                                System.out.println("Please enter y/n");
                        }
                    }
                }
                // incorrect password
                else{
                    flush();
                    System.out.println("Incorrect Password!!!");
                }
            }
            else{
                flush();
                System.out.println("Error!!! Username not Found");
            }
        }
    }

    // Password manager. Registor Menu.
    public static void manager_reg(){
        String username = "", password, salt, hash;
        boolean con = true;
        float score;

        while(con){
            try{
                System.out.print("Please Username: ");
                username = key.next().toLowerCase();
                
                File handle = new File(".\\Profiles\\" + username + ".txt");
                
                if(handle.createNewFile()){
                    con = false;
                }
                else{
                    flush();
                    System.out.println("Error!!! This Username is Taken");
                }
            }
            
            catch (IOException e){
                System.out.println("An Error Occoured");
            }
        }
        
        if(!con) {
            System.out.print("Please enter the Password: ");
            password = key.next();

            // Show Password score for the profile Password.
            score = (float) pas_score(password);
            System.out.println("\nProfile Password Score: " + score);
            System.out.println("Profile Password comments: " + pas_comment(score) + "\n");

            salt = salt_gen(15);
            hash = hash(username, password, salt);
            
            // Writing the HASH, SALT to the User profile for Authentication
            write_hash(username, hash, salt);
            //flush();
            System.out.print("SUCCESS!!! Profile Generated for \"" + username + "\" ");
        }
        
    }
    
    // Login Options.
    public static void login_options(String username){
        char option;
        boolean con = true;
        
        while (con){
            System.out.print("Password manager\n\n1). Retrive\n2). Add\n3). Update\n4). Delete\n5). List Sites Saved\n0). back\n==> ");
            option = key.next().charAt(0);

            switch(option) {
                case '0':
                flush();
                    con = false;
                    break;

                case '1':
                flush();
                    System.out.println("Retrive Password\n");
                    login_retrive(username);
                    System.out.print("Press y to continue.\n==> ");
                    option = key.next().charAt(0);
                flush();
                break;

                case '2':
                flush();
                    System.out.println("Add Password\n");
                    login_add(username);
                    System.out.print("Press y to continue.\n==> ");
                    option = key.next().charAt(0);
                flush();
                break;

                case '3':
                flush();
                    System.out.println("Update Password\n");
                    login_update(username);
                    System.out.print("Press y to continue.\n==> ");
                    option = key.next().charAt(0);
                flush();
                break;

                case '4':
                flush();
                    System.out.println("Delete Site\n");
                    login_delete(username);
                    System.out.print("Press y to continue.\n==> ");
                    option = key.next().charAt(0);
                flush();
                break;

                case '5':
                flush();
                    System.out.println("View Saved Sites\n");
                    login_display(username);
                    System.out.print("Press y to continue.\n==> ");
                    option = key.next().charAt(0);
                flush();

                default:
                    flush();
                        System.out.println("WARNING: Enter a valid option!!!\n");
                    }
                    
                }
            }
            
    // login delete function
    public static void login_delete(String username){
        String site;
        int len = count_line(username);
        String[][] arr = load_site(username);
        String[][] new_arr = new String[2][len-1];
        int j =0;
        boolean found = false;

        System.out.print("Enter Site: ");
        site = key.next().toLowerCase();
        
        // Checking for the Site
        for(int i = 0; i<arr[0].length;i++){
            if(site.equals(arr[0][i])){
                found = true;
            }
        }

        if(found){
            for(int i = 0; i<arr[0].length; i++){
                if(!arr[0][i].equals(site)){
                    new_arr[0][j] = arr[0][i];
                    new_arr[1][j] = arr[1][i];
                    j++;
                }
            }
            write_arr(new_arr, username);
        }
        else{
            System.out.println("\nERROR!!! Site not found\n");
        }

    }
        
    // login update function
    public static void login_update(String username){
        String[][] arr = load_site(username);
        String site, password;
        boolean con = true;
        float score;

        while(con){
            System.out.print("Enter Site: ");
            site = key.next().toLowerCase();

            if(exist(site, arr)){
                System.out.print("Enter new Password: ");
                password = key.next();

                for(int i = 0; i<arr[0].length; i++){
                    if(arr[0][i].equals(site)){
                        arr[1][i] = password;
                        score = (float) pas_score(password);
                        System.out.println("\nNew Password Score: " + score);
                        System.out.println("New Password comments: " + pas_comment(score) + "\n");
                        write_arr(arr, username);
                        System.out.println("Your Password has been Updated.");
                        con = false;
                    }
                }
            }
            else{
                flush();
                System.out.println("\nERROR Site Not found!!!\n");
                con = false;
            }
        }
    }
        
    // login retrive function.
    public static void login_retrive(String username){
        String[][] arr = load_site(username);
        String site;
        boolean con = true;

        while(con){
            System.out.print("Enter Site: ");
            site = key.next().toLowerCase();

            for(int i = 0; i < arr[0].length; i++){
                if(arr[0][i].equals(site)){
                    System.out.println("Password: " + arr[1][i] + "\n");
                    con = false;
                }

            }
            if(con){
                flush();
                System.out.println("\nERROR Site not Found!!!\n");
                con = false;
            }
        }

    }

    // Adding passwords
    public static void login_add(String username){
        String site,password;
        boolean con = true;
        float score;
        String arr[][] = new String[2][];
        String new_arr[][] = new String[2][];

        while(con){
            System.out.print("Please Enter the Site: ");
            site = key.next().toLowerCase();
            arr = load_site(username);
            if(!exist(site, arr)){
                System.out.print("Please enter the password: ");
                password = key.next();
                score = (float) pas_score(password);
                System.out.println("\nPassword Score: " + score);
                System.out.println("Password comments: " + pas_comment(score) + "\n");
                new_arr = update_arr(arr, username, site, password);
                write_arr(new_arr, username);
                con = false;
            }
            else{
                flush();
                System.out.println("This Site already exists!!!");
            }
        }
    }
            
    // load site array.
    public static String[][] load_site(String username){
        int lines = count_line(username);
        String [][] criden = new String[2][lines];

        try{
            File handle = new File(".\\Profiles\\" + username + ".txt");
            Scanner out = new Scanner(handle);
            int i = 0;

            while (out.hasNextLine()) {
                criden[0][i] = out.next();
                criden[1][i] = out.nextLine().trim();

                i++;
            }
            out.close();
        }
        catch (IOException e){
            System.out.println("An Error Occoured");
        }

        return criden;
    }

    // Display
    public static void login_display(String username){
        String[][] arr = load_site(username);
        int total = arr[0].length - 1;

        System.out.println("\nList of all Sites");
        for(int i = 1; i < arr[0].length; i++){
            System.out.print(i + "). " + arr[0][i] + "\n");
        }
        System.out.println("\nTotal Passwords: " + total + "");
        System.out.println();
    }

    // Check if already exists
    public static boolean exist(String site, String[][] arr){
        boolean exist = false;

        for(int j = 0; j < 2; j++){
            for(String i: arr[j]){
                if(site.equals(i)){
                    exist = true;
                }
            }
        }

        return exist;
    }
    
    // Update array
    public static String[][] update_arr(String[][] arr, String username,String site, String pas){
        int len = count_line(username);
        String[][] new_arr = new String[2][len+1];

        try{
            File handle = new File(".\\Profiles\\" + username + ".txt");
            PrintWriter add = new PrintWriter(handle);

            for(int i = 0; i<2; i++){
                for(int j = 0; j<arr[i].length; j++){
                    new_arr[i][j] = arr[i][j];
                }
            }

            
            new_arr[0][len] = site;
            new_arr[1][len] = pas;
            
            add.close();
        }
        catch (IOException e){
            System.out.println("An Error Occoured");
        }

        return new_arr;
    }

    // Write to file
    public static void write_arr(String[][] arr,String username){
        try{
            File handle = new File(".\\Profiles\\" + username + ".txt");
            PrintWriter add = new PrintWriter(handle);
            int i = 0;

            while (i < arr[0].length){
                add.print(arr[0][i] + " ");
                add.println(arr[1][i]);

                i++;
            }

            add.close();

        }
        catch(IOException e){
            System.out.println("An Error Occoured");
        }
    }

    // total lines count function.
    public static int count_line(String name){
        int count = 0;

        try{
            File handle = new File(".\\Profiles\\" + name + ".txt");
            Scanner out = new Scanner(handle);
            while (out.hasNextLine()) {
                out.nextLine();
                count ++;
            }

            out.close();
        }
        catch(IOException e){
            System.out.println("An Error Occoured");
        }

        return count;
    }

    // Authentication Function for Username and password authentication
    public static boolean manager_authen(String username, String password){
        String salt = "", hash = "", new_hash;
        boolean verify = false;

        try{
            File handle = new File(".\\Profiles\\" + username + ".txt");
            Scanner out = new Scanner(handle);

            salt = out.next();
            hash = out.next();
            out.close();
        }
        catch (IOException e){
            System.out.println("An Error Occoured");
        }
        finally{
            new_hash = hash(username.toLowerCase(), password, salt);
            if (hash.equals(new_hash)){
                verify = true;
            }
        }

        return verify;
    }

    // Writing the AUTHETICATION hash to the user profile.
    public static void write_hash(String username, String hash, String salt){

        try{
            File handle = new File(".\\Profiles\\" + username + ".txt");
            PrintWriter add = new PrintWriter(handle);

            add.print(salt + " ");
            add.println(hash);

            add.close();
        }
        catch (IOException e){
            System.out.println("An Error Occoured");
        }
    }

    // sha-256 hash function.
    public static String sha_256(String pas){
        String hash = "NULL";

        try{
            // Selecting the Algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-256");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] algo = md.digest(pas.getBytes());
            BigInteger no = new BigInteger(1, algo);

            // converting message digest into hex value.
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            hash = hashtext;
        }
        catch (NoSuchAlgorithmException | RuntimeException e){
            System.out.println("Error in Hashing Algo");
        }
        return hash;
    }

    // Hash Creaction function.
    public static String hash(String user, String pas, String salt){
        String hash, tmp;
        
        tmp = salt + user + pas + salt;
        hash = sha_256(tmp);

        return hash;
    }

    // Salt Generator
    public static String salt_gen(int len){
        String tmp = "";
        int rand;
        char num;

        for (int i = 0; i < len; i++){
            rand = (int)(Math.random() * 10);
            num = (char)(rand + 48);
            tmp += num;
        }

        return tmp;
    }

    // char shift encryption algo.
    public static String rot3_enc(String pas){
        char[] characters = pas.toCharArray();
        String enc_pas = "";

        for(char i: characters){
            i += 3;
            enc_pas += i;
        }

        return enc_pas;
    }

    // char shift decryption algo.
    public static String rot3_dec(String pas){
        char[] characters = pas.toCharArray();
        String dec_pas = "";

        for(char i: characters){
            i -= 3;
            dec_pas += i;
        }

        return dec_pas;
    }

    // Password Strength Check + Creation functions.
    public static void option2(){
        boolean con = true;

        while (con){
            char option;
            String password;
            float score;
            System.out.print("Passwrod Generation and Verification\n\n1). Password Generation.\n2). Password Scoring\n0). To go BACK\n==> ");

            option = key.next().charAt(0);

            switch (option) {
                case '0':
                flush();
                    con = false;
                    break;

                case '1':
                flush();
                    password = pas_generator();
                    System.out.println("\nThe password is: \"" + password + "\"");
                    score = (float) pas_score(password);
                    System.out.println("Score: " + score);
                    System.out.println("Comments: \"" + pas_comment(score) + "\"");
                    System.out.print("Press y to continue.\n==> ");
                    option = key.next().charAt(0);
                flush();
                    break;

                case '2':
                flush();
                    System.out.print("Please enter your Password\n==> ");
                    password = key.next();
                    score = (float) pas_score(password);
                    System.out.println("\nYour Password score is: " + score);
                    System.out.println("Comment: \" " + pas_comment(score) + " \"");
                    System.out.print("Press y to continue.\n==> ");
                    option = key.next().charAt(0);
                flush();
                    break;

                default:
                flush();
                    System.out.println("WARNING: Enter a valid option!!!\n");
            }
        }
    }

    // Password generation function.
    public static String pas_generator(){
        boolean lim = true;
        String pas = "";
        char[] alps = new char[52];
        char[] nums = new char[10];
        char[] rand = new char[10];

        // A,a,#,0
        int[] start = {97,65,48,33};
        int y,z,in = 0,check = 0;

        // Initialize alphabets
        for(int i = 0; i<2; i++){
            z = start[i];
            for(int j = z; j <( z + 26 ); j++ ){
                alps[in] = (char) j;
                in++;
            }
        }

        // Initialize Numbers
        for(int i = start[2], j = 0; i < (start[2] + 10); i++,j++){
            nums[j] = (char) i;
        }

        // Initialize Random Characters
        for(int i = start[3], j = 0; i < (start[3] + 10); i++,j++){
            rand[j] = (char) i;
        }

        System.out.print("Please enter password Length: ");
        in = key.nextInt();

        // logic for password generation.
        while(lim){
            z = (int) (Math.random() * 10);

            if (z < 5){
                y = (int) (Math.random() * 52);
                pas = pas + alps[y];
            }

            else if (z < 8){
                y = (int) (Math.random() * 10);
                pas = pas + nums[y];
            }

            else{
                y = (int) (Math.random() * 10);
                pas = pas + rand[y];
            }

            check ++;

            if (check == in){
                lim = false;
            }
        }

        return pas;
    }
    
    // Password scoring function.
    public static double pas_score(String pas){
        
        // Scores
        final int alp_low = 2;
        final int num = 3;
        final float spec = 4.5f;
        final float alp_up = 2.5f;

        // Char sets
        char ch;

        // Score variable
        float score;

        // length based scoring
        if(pas.length() < 6){
            score = -15;
        }
        else{
            score = 6;
            for(int i = 0; i < (pas.length() - 5) || i < 5; i++){
                score += 2;
            }
        }

        // character based scoring
        for(int i = 0; i < pas.length(); i++){
            ch = pas.charAt(i);

            if(ch > 96 && ch < 123){
                score += alp_low;
            }
            else if(ch > 64 && ch < 91){
                score += alp_up;
            }
            else if(ch > 47 && ch < 58){
                score += num;
            }
            else{
                score += spec;
            }
        }

        return score;
    }

    // To give Comments on the Password Score.
    public static String pas_comment(float score){
        if(score < 15){
            return "Extremely Weak Password";
        }
        else if(score < 35){
            return "Very Weak Password";
        }
        else if(score < 45){
            return "Weak Password";
        }
        else if(score < 55){
            return "Mediorcre Password";
        }
        else if(score < 65){
            return "Strong Password";
        }
        else if(score < 75){
            return "Very Strong Password";
        }
        else if(score < 85){
            return "Extremely Strong Password";
        }
        else{
            return "Fortknox Kind of Security";
        }
    }

    // This Function was made to Clear the console screen.
    public static void flush(){
        try{  
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch (IOException | InterruptedException e){
            System.out.println("An Error occured during Screen Clearing.");
        }
    }  
}
