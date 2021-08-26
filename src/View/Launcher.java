package View;

import Model.Dish;
import Model.Restaurant;
import Utils.DocxWriter;

import java.io.IOException;
import java.util.List;

public class Launcher {
    public static void main(String[] args) {
        // See https://stackoverflow.com/a/52654791/3956070 for explanation
        try{
            CSV_Main.main(args);
        }catch (IOException e){
            e.getMessage();
        }
        Main.main(args);
        List<Dish> ts = Restaurant.getInstance().getProfitRelation().stream().toList();
        String[] prompt = new String[ts.size()];
        for (int i = 0; i < prompt.length; i++) {
            prompt[i] = String.format("%s profit: %.2f",ts.get(i).getDishName(), ts.get(i).getPrice());
            if(i == 0){
                prompt[0] = "🥇 " + prompt[0];
            }
            else if(i == 1){
                prompt[1] = "🥈 " + prompt[1];
            }
            else if(i == 2){
                prompt[2] = "🥉 " + prompt[2];
            }
            else{
                prompt[i] = i + ") "+ prompt[i];
            }
        }
        DocxWriter.getInstance().writeToDocx("profits.docx", "JavaEat Profits List", prompt);
    }
}
