import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws IOException {
        Customer customer = new Customer("Suresh", 123456, 123, 0);
        Scanner myInp = new Scanner(System.in);
        int logintry = 0;
        int attemptLeft = 3;
        do {
            System.out.println("*******************************************************");
            System.out.println("Welcome to Indian Bank Login Page!");
            System.out.println("*******************************************************");
            System.out.println("Please enter acccount #");
            int acc = myInp.nextInt();
            //System.out.println(acc);
            System.out.println("Please enter the password");
            int pass = myInp.nextInt();
            if (acc == customer.getAccno() && pass == customer.getPass()) {
                System.out.println("login successfull");
                System.out.println("Please choose the option from below");
                boolean input = false;
                do {

                    System.out.println("*******************************************************");
                    System.out.println("    !!!!!!!Welcome to Indian Bank !!!!!!");
                    System.out.println("*******************************************************");
                    System.out.println("1. Deposit");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Transfer");
                    System.out.println("4. Mini statement");
                    System.out.println("0. Logout");
                    PrintWriter pw = new PrintWriter(new FileOutputStream("transactions.txt", true));
                    int opt = myInp.nextInt();
                    switch (opt) {
                        case 1:
                            System.out.println("please enter amount to deposit");
                            int amt = myInp.nextInt();
                            customer.setBal(customer.getBal()+amt);
                            pw.println("Amount " +amt+ " deposited successfully");
                            System.out.println("--------------------------------------------------");
                            System.out.println("Account balance post deposit is: "+customer.getBal());
                            System.out.println("--------------------------------------------------");
                            pw.close();
                            input = true;
                            break;
                        case 2:
                            System.out.println("please enter amount to withdraw");
                            int wamt = myInp.nextInt();
                            if (wamt<= customer.getBal()){
                                customer.setBal((customer.getBal()-wamt));
                                pw.println("Amount " +wamt+ " withdraw successfully");
                                System.out.println("--------------------------------------------------");
                                System.out.println("Account balance post withdraw is: "+customer.getBal());
                                System.out.println("--------------------------------------------------");
                                pw.close();
                                input = true;
                                break;
                            }
                            else {
                                System.out.println("Insufficient balance!, current balance is: "+customer.getBal());
                                input = true;
                                break;
                            }
                        case 3:
                            System.out.println("Enter benificiary a/c #");
                            int bname = myInp.nextInt();
                            System.out.println("Enter the transfer amount");
                            int tamt = myInp.nextInt();
                            if (tamt<= customer.getBal()) {
                                OkHttpClient client = new OkHttpClient();

                                okhttp3.Request request = new okhttp3.Request.Builder().url("http://2factor.in/API/V1/8700d13b-fd04-11eb-a13b-0200cd936042/SMS/9036993870/AUTOGEN").get()
                                        .addHeader("content-type", "application/x-www-form-urlencoded")
                                        .build();
                                Response response = client.newCall(request).execute();
                                if (response.code() == 200) {
                                    System.out.println("OTP send successfully!");
                                    String json = response.body().string();
                                    //System.out.println(json);
                                    JSONObject Jobject = new JSONObject(json);
                                    //System.out.println(Jobject);
                                    String sid = (String) Jobject.get("Details");
                                    //System.out.println(sid);
                                    System.out.println("Please enter OTP sent to 9036993870 to continue");
                                    int otp = myInp.nextInt();
                                    OkHttpClient client1 = new OkHttpClient();
                                    okhttp3.Request request1 = new okhttp3.Request.Builder().url("http://2factor.in/API/V1/8700d13b-fd04-11eb-a13b-0200cd936042/SMS/VERIFY/" + sid + "/" + otp).get()
                                            .addHeader("content-type", "application/x-www-form-urlencoded")
                                            .build();
                                    Response response1 = client1.newCall(request1).execute();
                                    if (response1.code() == 200) {
                                        System.out.println("OTP Verified successfully!");
                                        customer.setBal((customer.getBal())-tamt);
                                        pw.println("Amount " + tamt + " has been transfered to " + bname + " successfully");
                                        pw.close();
                                        System.out.println("Amount " + tamt + " has been transfered to " + bname + " successfully");
                                        System.out.println("--------------------------------------------------");
                                        System.out.println("Account balance post transfer is: "+customer.getBal());
                                        System.out.println("--------------------------------------------------");
                                        input = true;
                                        break;
                                    } else {
                                        System.out.println("Wrong OTP!, please try again later");
                                        input = true;
                                        break;
                                    }
                                } else {
                                    System.out.println("System issue while sending OTP!, please try again later");
                                    input = true;
                                    break;
                                }
                            }
                            else {
                                System.out.println("Insufficient balance!, current balance is: "+customer.getBal());
                                input = true;
                                break;
                            }

                        case 4:
                            String fileName = "transactions.txt";
                            String line;

                            int i = 0;
                            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName)))
                            {
                                while(((line = bufferedReader.readLine()) != null) && i < 5) {
                                    System.out.println(line);
                                    i++;
                                }
                                input = true;
                                break;
                            }

                        case 0:
                            input = false;
                            exit(0);
                    }
                } while (input);
            }
             else {
                 attemptLeft-=1;
                System.out.println("Invalidate A/c number or password "+attemptLeft+" attempt left");
                logintry += 1;
            }
        }
        while (logintry < 3);
        System.out.println("Maximum attempt reached");
    }
}