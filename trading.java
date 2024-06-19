import java.util.ArrayList;
import java.util.Scanner;

public class trading {

public static void displayStocks(ArrayList<Stock> stocks){
for(Stock stock : stocks){
    stock.getStock();
}
}

public static void displayPortfolio(Player player){
    ArrayList<Stock> result=player.getPortfolio();
    for(Stock stock : result){
        System.out.println(
                "Stock{" +
                        "symbol='" + stock.getSymbol() + '\'' +
                        ", name='" + stock.getName() + '\'' +
                        ", price=" + stock.getPrice() +
                        ", quantity=" + stock.getQuantity()+
                        '}');
    }
    }

public static void displayFunds(Player player){
    System.out.println("Your funds: "+player.getFunds());
}
public static void buyStocks(Player player,ArrayList<Stock> stocks, String symbol, int quantity){
    boolean stockFound = false; 
    for(Stock stock : stocks){
        if ((stock.getSymbol().equalsIgnoreCase(symbol))){
            if (stock.getQuantity()>quantity){
                double priceToPay=quantity*stock.getPrice();
                double current=player.getFunds();
                if(priceToPay<=current){
                stockFound = true;
                System.out.println("Yay you got em");
                player.setFunds(current-priceToPay);
                player.setPortfolio(stock,quantity);

                stock.setQuantity((stock.getQuantity()-quantity));
                
                break;
                }
            else{
                System.out.println("You dont have enough funds..");
            }
            }
        else{
                System.out.println("Sorry your required quantity is unavailable. There are only "+stock.getQuantity()+" number of stocks available");
            }
        }
    }
    if (!stockFound) { // Print message if no matching stock is found
        System.out.println("No such stock exists!");
    }
    
}

public static void sellStocks(Player player, ArrayList<Stock> stocks, String symbol, int quantity) {
    boolean stockOwned = false;

    for (Stock stock : player.getPortfolio()) {
        if (stock.getSymbol().equals(symbol)) {
            stockOwned = true;
            if (stock.getQuantity() >= quantity) {
                player.setFunds(player.getFunds() + quantity * stock.getPrice());
                stock.setQuantity(stock.getQuantity() - quantity);
                for (Stock stock2 : stocks) {
                    if (stock2.getSymbol().equals(symbol)) {
                        stock2.setQuantity(stock2.getQuantity() + quantity);
                    }
                }
                break; // Exit the loop once the stock is sold
            } else {
                System.out.println("You dont have that much quantity of the stock. You only have " + stock.getQuantity() + " stocks.");
                return; // Exit the method if the player doesn't own enough of the stock
            }
        }
    }

    // If the stock is not found in the portfolio
    if (!stockOwned) {
        System.out.println("You dont own any such stock!");
    }
}


// MAIN CLASS
public static void main(String[] args) {

        ArrayList<Stock> stocks=new ArrayList<>();

        stocks.add(new Stock("AAPL", "Apple Inc.", 150.0,10));
        stocks.add(new Stock("GOOGL", "Alphabet Inc.", 2500.0,23));
        stocks.add(new Stock("MSFT", "Microsoft Corporation", 300.0,12));

        Scanner scanner=new Scanner(System.in);

        System.out.println("Welcome to the trading simulation game!");

        // Prompt the user for their name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);
        // player.displayFunds();


        while (true) {
            // Display options to the player
            System.out.println("1. View stocks");
            System.out.println("2. View portfolio");
            System.out.println("3. Buy stocks");
            System.out.println("4. Sell stocks");
            System.out.println("5. Check my funds");
            System.out.println("6. Quit");

            // Get player's choice
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // View available stocks
                    displayStocks(stocks);
                    break;
                case 2:
                    // View player's portfolio
                    displayPortfolio(player);
                    break;
                case 3:
                    // Buy stocks
                    System.out.print("Enter stock symbol: ");
                    String stockSymbol = scanner.nextLine();
                    
                    System.out.print("Enter quantity you want to buy: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    buyStocks(player,stocks,stockSymbol,quantity);
                    break;
                case 4:
                    // Sell stocks
                    System.out.print("Enter stock symbol: ");
                    String stockSymbolSell = scanner.nextLine();
                    
                    System.out.print("Enter quantity you want to sell: ");
                    int sellQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    sellStocks(player,stocks,stockSymbolSell,sellQuantity);
                    break;
                case 5:
                    displayFunds(player);
                    break;
                case 6:
                    // Quit the game
                    System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

//  STOCK CLASS
class Stock{
    private String name;
    private String symbol;
    private double price;
    private int quantity;

    public Stock(String name, String symbol, double price,int quantity){
        this.name=name;
        this.symbol=symbol;
        this.price=price;
        this.quantity=quantity;
    }

    // getter
    public void getStock(){
        System.out.println(
                "Stock{" +
                        "symbol='" + symbol + '\'' +
                        ", name='" + name + '\'' +
                        ", price=" + price +
                        ", quantity=" + quantity +
                        '}');
    
    }
    public String getName(){
        return name;
    }
    public String getSymbol(){
        return symbol;
    }
    public double getPrice(){
        return price;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
}

// PLAYER CLASS
class Player{
    private String name;
    private double funds;
    private ArrayList<Stock> portfolio;

    public Player(String name) {
        this.name = name;
        this.funds = 5000.00;
        this.portfolio = new ArrayList<>();
    }

    //getter
    public double getFunds(){
        // System.out.println("available funds":funds);
        return funds;
    }

    //setter
    public void setFunds(double fund){
        funds=fund;
    }
    
    //getter
    public ArrayList<Stock> getPortfolio() {
        ArrayList<Stock> result = new ArrayList<>();
        for (Stock stock : portfolio) {
            result.add(stock);
        }
        return result;
    }
    
    // setter
public void setPortfolio(Stock stock, int quantity) {
    boolean stockExists = false;

    // Check if the stock already exists in the portfolio
    for (Stock existingStock : portfolio) {
        if (existingStock.getSymbol().equals(stock.getSymbol())) {
            // Update the quantity of the existing stock
            existingStock.setQuantity(existingStock.getQuantity() + quantity);
            stockExists = true;
            break;
        }
    }

    // If the stock doesn't exist in the portfolio, add it
    if (!stockExists) {
        // portfolio.add(stock); // Add the new stock directly to the portfolio
        portfolio.add(new Stock(stock.getName(),stock.getSymbol(),stock.getPrice(),quantity));

    }
}


}
