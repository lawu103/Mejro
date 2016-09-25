package mejro;

import java.util.Scanner;
import java.util.Random;

public class PostApocalypticRPG 
{
    private static String difficulty; //difficulty
    
    private static int coord[] = {0, 0}; //player's location, X and Y
    private static int E7[] = {3, 0}; //E7's location, X (same for all difficulties except Blazing) and Y
    private static int X[] = {-2, 3}; //X window, min and max (same for all difficulties except Blazing)
    private static int Y[] = {0, 0}; //Y window, min and max
    
    private static int police1[] = {0, 0}; //coordinates of locations of interest
    private static int police2[] = {0, 0}; 
    private static int police3[] = {0, 0}; 
    
    private static int military[] = {0, 0};
    
    private static int camp1[] = {0, 0};
    private static int camp2[] = {0, 0};
    
    private static int grocery1[] = {0, 0};
    private static int grocery2[] = {0, 0};
    private static int grocery3[] = {0, 0};
    
    private static int police1ch, police2ch, police3ch, militarych, camp1ch, camp2ch, grocery1ch, grocery2ch, grocery3ch; //check locations
    
    private static int policew, policep, policesh, policea, policesn; //police station item spawn rates
    private static int militaryw, militaryp, militarysh, militarya, militarysn, SMWD; //military base item spawn rates including SMWD CDXX
    private static int groceryf; //food spawn rate
    private static int ammopickup; //ammo picked up from enemies
    
    private static int scavwea = 1; //found weapon level. Starting at 1, Pistol, Shotgun, Assault Rifle, Sniper Rifle
    private static String weapon0 = ("knife");
    private static String weapon1 = ("pistol");
    private static String weapon2 = ("");
    private static String weapon3 = ("");
    private static String weapon4 = ("");
    private static String weapon5 = ("");
    
    private static int enemysp, enemytype; //enemy frequency spawn rate, and type of enemy variable
    private static String enemy; //actual enemy to fight
    private static int enemyhp, enemydef, enemyatt, enemymag, enemydmg; //enemy health, defense, attack, weapon mag, and damage they deal
    private static String enemyammodr; //enemy ammo drop
    
    private static int ammo[] = {0, 0, 0, 0}; //ammo list: pistol, shotgun, assault rifle, sniper rifle
    
    private static String weapon; //weapon player has selected. Default selection to start is pistol
    private static int weaponammo, weaponmag; //ammo for selected weapon, and magazine size. Default is pistol
    private static int weapondmg; //damage of the selected weapon
    
    private static int playerdmg, playerdef = 20, hp = 100, rest = 110, food; //damage player deals, player defense, player health, player rest level, and food supplies
    private static String playerturn, weaponswitch; //player turn choice, weapon player switches to
    
    private static int day = 0; //counts days
    private static int score, kills = 0, difficultyMultiplier; //variables for calculating score
    
    public static void main(String [] args) //introduction
    {
        Scanner scan = new Scanner (System.in);
        System.out.println("2033. The world has been engulfed by atomic fire. For months you have wandered through this destroyed city, caring only about surviving to see tomorrow. But then you");
        System.out.println("heard of a 'safe haven': a rumored bunker from the old world that survived the war. The survivors you've met - the ones who didn't try to kill you, that is - spoke");
        System.out.println("of unimaginable wealth inside the bunker. They say there is enough medicine and canned food inside it to sustain a thousand people for a hundred years!");
        System.out.println("");
        System.out.println("Now your goal is to reach the legendary E7 bunker complex, braving threats both human and mutant on the way there. Will you be able to reach the safe haven and open");
        System.out.println("its treasures to the world? Can you survive to see another day?");
        System.out.println("");
        System.out.println("...");
        System.out.println("");
        System.out.println("Run tutorial?");
        tutorialChoose();
    }
    
    public static void tutorialChoose() //choose whether to run tutorial
    {
        Scanner scan = new Scanner (System.in);
        String contToTutorial;
        System.out.println("[YES]");
        System.out.println("[NO]");
        contToTutorial = scan.nextLine();
        contToTutorial = contToTutorial.toLowerCase();
        if (contToTutorial.equals("yes"))
        {
            tutorial();
        }
        else if (contToTutorial.equals("no"))
        {
            System.out.println("Choose difficulty:");
            difficulty();
        }
        else
        {
            System.out.println("Command not recognized. Please try again.");
            tutorialChoose();
        }
    }
    
    public static void tutorial() //actual tutorial
    {
        Scanner scan = new Scanner (System.in);
        String contToGame;
        System.out.println("Commands are in [] brackets. Capitilization does not matter when entering commands.");
        System.out.println("General commands may be entered at any time, except during a battle. They include:");
        System.out.println(" - [INVENTORY] Lists your weapons, ammo, and food supplies.");
        System.out.println(" - [MAP] Brings up your current location as well as any locations of interest.");
        System.out.println(" - [STATS] Brings up your health and rest levels.");
        System.out.println("Every day, you consume 3 food and lose 15 rest (out of 100).");
        System.out.println("Choosing to rest takes up one day. Resting will resupply your rest and health levels completely.");
        System.out.println("Choosing to travel moves you one space in the respective direction.");
        System.out.println("If you run out of food supplies or let your rest level reach 0, you will die.");
        System.out.println("Good luck.");
        System.out.println("");
        System.out.println("(Enter anything to continue...)");
        contToGame = scan.nextLine();
        
        if (contToGame.equals("a"))
        {
            System.out.println("Choose difficulty:");
            difficulty();
        }
        else
        {
            System.out.println("Choose difficulty:");
            difficulty();
        }
    }
    
    public static void difficulty() //choosing difficulty
    {
        Scanner scan = new Scanner (System.in);
        System.out.println("[EASY] Less distance to travel, more plentiful supplies, and less frequent enemies.");
        System.out.println("[NORMAL] The standard.");
        System.out.println("[HARD] More distance to travel, less plentiful supplies, and more frequent enemies.");
        System.out.println("[BLAZING] You will not survive.");
        difficulty = scan.nextLine();
        difficulty = difficulty.toLowerCase();
        if (difficulty.equals("easy"))
        {
            E7[1] = 3; //set E7 Y value
            Y[0] = -2; //set Y window
            Y[1] = 3; 
            food = 21; //starting food
            ammo[0] = 32; //starting pistol ammo
            weapon = weapon1; //default equipped weapon (pistol)
            weaponammo = ammo[0]; //default equipped weapon's (pistol's) ammo
            difficultyMultiplier = 1; //sets difficulty multiplier for score
            locations(); //sets locations of interest
            day(); //goes to game
        }
        else if (difficulty.equals("normal"))
        {
            E7[1] = 4;
            Y[0] = -3;
            Y[1] = 4;
            food = 21;
            ammo[0] = 24;
            weapon = weapon1;
            weaponammo = ammo[0];
            difficultyMultiplier = 2;
            locations();
            day();
        }
        else if (difficulty.equals("hard"))
        {
            E7[1] = 5;
            Y[0] = -4;
            Y[1] = 5;
            food = 21;
            ammo[0] = 16;
            weapon = weapon1;
            weaponammo = ammo[0];
            difficultyMultiplier = 3;
            locations();
            day();
        }
        else if (difficulty.equals("blazing"))
        {
            E7[0] = 4; //set E7's special coordinates
            E7[1] = 20;
            X[0] = 0; //set special X window
            X[1] = 4;
            Y[0] = 0;
            Y[1] = 20;
            food = 42;
            ammo[0] = 8;
            weapon = weapon1;
            weaponammo = ammo[0];
            difficultyMultiplier = 4;
            locations();
            day();
        }
        else 
        {
            System.out.println("Command not recognized. Please try again.");
            difficulty();
        }
    }
    
    public static void spawn() //sets item spawn rates based on difficulty
    {
        Random randomGenerator = new Random();
        if (difficulty.equals("easy")) 
        {
            policew = randomGenerator.nextInt(2) + 1; 
            policep = randomGenerator.nextInt(1) + 1; 
            policesh = randomGenerator.nextInt(1) + 1; 
            policea = randomGenerator.nextInt(2) + 1; 
            policesn = randomGenerator.nextInt(3) + 1; 
            
            militaryw = randomGenerator.nextInt(1) + 1;  
            militaryp = randomGenerator.nextInt(1) + 1; 
            militarysh = randomGenerator.nextInt(1) + 1; 
            militarya = randomGenerator.nextInt(1) + 1; 
            militarysn = randomGenerator.nextInt(2) + 1;
            
            groceryf = 30;
        }
        else if (difficulty.equals("normal"))
        {
            policew = randomGenerator.nextInt(3) + 1; 
            policep = randomGenerator.nextInt(2) + 1; 
            policesh = randomGenerator.nextInt(2) + 1; 
            policea = randomGenerator.nextInt(3) + 1; 
            policesn = randomGenerator.nextInt(4) + 1; 
            
            militaryw = randomGenerator.nextInt(1) + 1;  
            militaryp = randomGenerator.nextInt(1) + 1; 
            militarysh = randomGenerator.nextInt(1) + 1; 
            militarya = randomGenerator.nextInt(2) + 1; 
            militarysn = randomGenerator.nextInt(3) + 1; 
            
            groceryf = randomGenerator.nextInt((30 - 20) + 1) + 20;
        }
        else if (difficulty.equals("hard"))
        {
            policew = randomGenerator.nextInt(4) + 1; 
            policep = randomGenerator.nextInt(3) + 1; 
            policesh = randomGenerator.nextInt(3) + 1; 
            policea = randomGenerator.nextInt(4) + 1; 
            policesn = randomGenerator.nextInt(5) + 1; 
            
            militaryw = randomGenerator.nextInt(2) + 1;  
            militaryp = randomGenerator.nextInt(2) + 1; 
            militarysh = randomGenerator.nextInt(2) + 1; 
            militarya = randomGenerator.nextInt(3) + 1; 
            militarysn = randomGenerator.nextInt(4) + 1; 
            
            groceryf = randomGenerator.nextInt((30 - 10) + 1) + 10;
        }
        else if (difficulty.equals("blazing"))
        {
            policew = randomGenerator.nextInt(5) + 1; //equivalent of 4/20 chance
            policep = randomGenerator.nextInt(5) + 1; 
            policesh = randomGenerator.nextInt(5) + 1; 
            policea = randomGenerator.nextInt(5) + 1; 
            policesn = randomGenerator.nextInt(5) + 1; 
            
            militaryw = randomGenerator.nextInt(5) + 1;  
            militaryp = randomGenerator.nextInt(5) + 1; 
            militarysh = randomGenerator.nextInt(5) + 1; 
            militarya = randomGenerator.nextInt(5) + 1; 
            militarysn = randomGenerator.nextInt(5) + 1; 
            SMWD = randomGenerator.nextInt(2) + 1; //SMWD CDXX exclusive to blazing
            
            groceryf = randomGenerator.nextInt((20 - 4) + 1) + 4;
        }
    }
    
    public static void enemySpawn() //sets enemy spawn rates based on difficulty
    {
        Random randomGenerator = new Random();
        if (difficulty.equals("easy"))
        {
            enemysp = randomGenerator.nextInt(6) + 1;
        }
        else if (difficulty.equals("normal"))
        {
            enemysp = randomGenerator.nextInt(5) + 1;
        }
        else if (difficulty.equals("hard"))
        {
            enemysp = randomGenerator.nextInt(4) + 1;
        }
        else if (difficulty.equals("blazing"))
        {
            enemysp = randomGenerator.nextInt(5) + 1; //equivalent of 4/20
        }
        
        enemytype = randomGenerator.nextInt(10) + 1; //chooses potential enemy type
        if (enemytype == 1 || enemytype == 2 || enemytype == 3 || enemytype == 4)
        {
            enemy = ("Mutant");
            enemyhp = 150;
            enemyatt = 25;
            enemydef = 15;
            enemymag = Integer.MAX_VALUE;
        }
        else if (enemytype == 5 || enemytype == 6)
        {
            enemy = ("Scout");
            enemyhp = 100;
            enemyatt = 35;
            enemydef = 10;
            enemymag = 8;
            enemyammodr = ("pistol");
        }
        else if (enemytype == 7 || enemytype == 8)
        {
            enemy = ("Hunter");
            enemyhp = 100;
            enemyatt = 50;
            enemydef = 5;
            enemymag = 2;
            enemyammodr = ("shotgun");
        }
        else if (enemytype == 9)
        {
            enemy = ("Stormtrooper");
            enemyhp = 100;
            enemyatt = 40;
            enemydef = 15;
            enemymag = 30;
            enemyammodr = ("assault rifle");
        }
        else if (enemytype == 10)
        {
            enemy = ("Sniper");
            enemyhp = 100;
            enemyatt = 70;
            enemydef = 20;
            enemymag = 5;
            enemyammodr = ("sniper");
        }
    }

    public static void locations() //sets places of interests
    {
        Random randomGenerator = new Random();
        police1[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
        police1[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        while (police1[0] == 0 && police1[1] == 0) //makes sure locations aren't at (0, 0)
        {
            police1[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
            police1[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        }
        police2[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
        police2[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        while ((police2[0] == police1[0] && police2[1] == police1[1]) || (police2[0] == 0 && police2[1] == 0)) //makes sure locations don't occupy the same place
        {
            police2[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
            police2[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        }
        police3[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
        police3[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        while ((police3[0] == police1[0] && police3[1] == police1[1]) || (police3[0] == police2[0] && police3[1] == police2[1]) || (police3[0] == 0 && police3[1] == 0))
        {
            police3[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
            police3[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        }
        
        military[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
        military[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        while ((military[0] == police1[0] && military[1] == police1[1]) || (military[0] == police2[0] && military[1] == police2[1]) || (military[0] == police3[0] && military[1] == police3[1]) 
                || (military[0] == 0 && military[1] == 0))
        {
            military[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
            military[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        }
        
        camp1[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
        camp1[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        while ((camp1[0] == police1[0] && camp1[1] == police1[1]) || (camp1[0] == police2[0] && camp1[1] == police2[1]) || (camp1[0] == police3[0] && camp1[1] == police3[1]) 
                || (camp1[0] == military[0] && camp1[1] == military[1]) || (camp1[0] == 0 && camp1[1] == 0))
        {
            camp1[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
            camp1[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        }
        camp2[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
        camp2[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        while ((camp2[0] == police1[0] && camp2[1] == police1[1]) || 
                (camp2[0] == police2[0] && camp2[1] == police2[1]) || (camp2[0] == police3[0] && camp2[1] == police3[1]) || (camp2[0] == military[0] && camp2[1] == military[1]) 
                || (camp2[0] == camp1[0] && camp2[1] == camp1[1]) || (camp2[0] == 0 && camp2[1] == 0))
        {
            camp2[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
            camp2[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        }
        
        grocery1[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
        grocery1[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        while ((grocery1[0] == police1[0] && grocery1[1] == police1[1]) || (grocery1[0] == police2[0] && grocery1[1] == police2[1]) || (grocery1[0] == police3[0] && grocery1[1] == police3[1]) 
                || (grocery1[0] == military[0] && grocery1[1] == military[1]) || (grocery1[0] == camp1[0] && grocery1[1] == camp1[1]) || (grocery1[0] == camp2[0] && grocery1[1] == camp2[1])
                || (grocery1[0] == 0 && grocery1[1] == 0))
        {
            grocery1[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
            grocery1[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        }
        grocery2[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
        grocery2[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        while ((grocery2[0] == police1[0] && grocery2[1] == police1[1]) || (grocery2[0] == police2[0] && grocery2[1] == police2[1]) || (grocery2[0] == police3[0] && grocery2[1] == police3[1]) 
                || (grocery2[0] == military[0] && grocery2[1] == military[1]) || (grocery2[0] == camp1[0] && grocery2[1] == camp1[1]) || (grocery2[0] == camp2[0] && grocery2[1] == camp2[1]) 
                || (grocery2[0] == grocery1[0] && grocery2[1] == grocery1[1]) || (grocery2[0] == 0 && grocery2[1] == 0))
        {
            grocery2[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
            grocery2[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        }
        grocery3[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
        grocery3[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        while ((grocery3[0] == police1[0] && grocery3[1] == police1[1]) || (grocery3[0] == police2[0] && grocery3[1] == police2[1]) || (grocery3[0] == police3[0] && grocery3[1] == police3[1]) 
                || (grocery3[0] == military[0] && grocery3[1] == military[1]) || (grocery3[0] == camp1[0] && grocery3[1] == camp1[1]) || (grocery3[0] == camp2[0] && grocery3[1] == camp2[1]) 
                || (grocery3[0] == grocery1[0] && grocery3[1] == grocery1[1]) || (grocery3[0] == grocery2[0] && grocery3[1] == grocery2[1]) || (grocery3[0] == 0 && grocery3[1] == 0))
        {
            grocery3[0] = randomGenerator.nextInt((X[1] - X[0]) + 1) + X[0];
            grocery3[1] = randomGenerator.nextInt((Y[1] - Y[0]) + 1) + Y[0];
        }
    }
    
    public static void day()
    {
        if (coord[0] == E7[0] & coord[1] == E7[1]) //once player reaches E7
        {
            day = day + 1;
            System.out.println("After " + day + " days, you finally arrive at E7. The treasures of the old world are all yours now...\n...\nYOU WIN!");
            score = ((day*10) + (kills*100))*difficultyMultiplier;
            System.out.println("Your score: " + score);
        }
        else //as long as player is not at E7, this happens
        {
            day = day + 1; //increase day count
            food = food - 3; //consume food
            rest = rest - 15; //lose rest
            if (food <= 0) //death if food or rest gets to 0
            {
                System.out.println("You try to keep going, but you finally lose the battle against hunger. \nYOU HAVE DIED."); 
                System.exit(1); //game stop
            }
            else if (rest <= 0) 
            {
                System.out.println("You've gone for days without resting, and exhaustion finally catches up to you. You keel over onto the ground, where you sleep for the last time.\nYOU HAVE DIED."); 
                System.exit(1); //game stop
            }
            //otherwise, continues with game
            
            System.out.println("Day " + day); //prints day    
            System.out.println("Your current location: (" + coord[0] + "," + coord[1] + ")"); //prints location
            
            enemySpawn(); //tests if player encounters enemy
            if (enemysp == 1) //if enemy encountered, enter battle
            {
                playerturn();
            }
            
            if (food <= 9) //warns player if food is low
            {
                System.out.println("WARNING: low food!"); 
            }
            if (rest <= 30 || hp < 100) //warns player if rest or health is low
            {
                System.out.println("WARNING: rest recommended!"); 
            }
            placecheck(); //checks if at location of interest. If not, continue
            dayAction();
            day(); //loops entire process
        }
    }
    
    public static void dayAction()
    {
        Scanner scan = new Scanner (System.in);
        String dayAction;
        System.out.println("Go [NORTH]");
        System.out.println("Go [SOUTH]");
        System.out.println("Go [EAST]");
        System.out.println("Go [WEST]");
        System.out.println("[REST]");
        dayAction = scan.nextLine();
        dayAction = dayAction.toLowerCase();
        if (dayAction.equals("north"))
        {
            coord[1] = coord[1] + 1; //changes coord values respectively
        }
        else if (dayAction.equals("south"))
        {
            coord[1] = coord[1] - 1;
        }
        else if (dayAction.equals("east"))
        {
            coord[0] = coord[0] + 1;
        }
        else if (dayAction.equals("west"))
        {
            coord[0] = coord[0] - 1;
        }
        else if (dayAction.equals("rest")) //refill health and rest
        {
            hp = 100;
            rest = 110;
            System.out.println("You rest for the day...");
        }
        else if (dayAction.equals("inventory"))
        {
            inventory(); //shows inventory and then returns to action choice
            dayAction();
        }
        else if (dayAction.equals("map"))
        {
            map(); //shows map and then returns to action choice
            dayAction();
        }
        else if (dayAction.equals("stats"))
        {
            stats(); //shows stats and then returns to action choice
            dayAction();
        }
        else
        {
            System.out.println("Command not recognized. Please try again.");
            dayAction();
        }
    }
    
    public static void placecheck() //checks if player is at place of interest
    {
        if (coord[0] == police1[0] && coord[1] == police1[1])
        {
            if (police1ch == 1) //checks if player has already visited location
            {
                System.out.println("You've already visited this location.");
            }
            else
            {
                police1ch = 1;
                spawn(); //runs item spawning
                policescav(); //runs police station scavenging
            }
        }
        else if (coord[0] == police2[0] && coord[1] == police2[1]) //police station 2
        {
            if (police2ch == 1) 
            {
                System.out.println("You've already visited this location.");
            }
            else
            {
                police2ch = 1;
                spawn(); 
                policescav(); 
            }
        }
        else if (coord[0] == police3[0] && coord[1] == police3[1]) //police station 3
        {
            if (police3ch == 1) 
            {
                System.out.println("You've already visited this location.");
            }
            else
            {
                police3ch = 1;
                spawn(); 
                policescav();  
            }
        }
        else if (coord[0] == military[0] && coord[1] == military[1]) //military base
        {
            if (militarych == 1) 
            {
                System.out.println("You've already visited this location.");
            }
            else
            {
                militarych = 1;
                spawn(); 
                militaryscav();  
            }
        }
        else if (coord[0] == camp1[0] && coord[1] == camp1[1]) //camp 1
        {
            if (camp1ch == 1) 
            {
                System.out.println("You've already visited this location.");
            }
            else
            {
                camp1ch = 1;
                System.out.println("You enter an enemy camp!");
                enemySpawn();
                playerturn();
            }
        }
        else if (coord[0] == camp2[0] && coord[1] == camp2[1]) //camp 2
        {
            if (camp2ch == 1) 
            {
                System.out.println("You've already visited this location.");
            }
            else
            {
                camp2ch = 1;
                System.out.println("You enter an enemy camp!");
                enemySpawn();
                playerturn();
            }
        }
        else if (coord[0] == grocery1[0] && coord[1] == grocery1[1]) //grocery 1
        {
            if (grocery1ch == 1) 
            {
                System.out.println("You've already visited this location.");
            }
            else
            {
                grocery1ch = 1;
                spawn(); 
                food = food + groceryf;
                System.out.println("You find " + groceryf + " cans of food in the ruins."); 
            }
        }
        else if (coord[0] == grocery2[0] && coord[1] == grocery2[1]) //grocery 2
        {
            if (grocery2ch == 1) 
            {
                System.out.println("You've already visited this location.");
            }
            else
            {
                grocery2ch = 1;
                spawn(); 
                food = food + groceryf;
                System.out.println("You find " + groceryf + " cans of food in the ruins.");
            }
        }
        else if (coord[0] == grocery3[0] && coord[1] == grocery3[1]) ///grocery 3
        {
            if (grocery3ch == 1) 
            {
                System.out.println("You've already visited this location.");
            }
            else
            {
                grocery3ch = 1;
                spawn(); 
                food = food + groceryf;
                System.out.println("You find " + groceryf + " cans of food in the ruins.");  
            }
        }
        else
        {
        }
    }
    
    public static void policescav()
    {
        if (policew == 1) //found weapon
        {
            scavwea = scavwea + 1; //increases weapon level
            weaponlevels(); //runs weapon scavenging
        }
        if (policep == 1) //found pistol ammo
        {
            ammo[0] = ammo[0] + 8;
            System.out.println("Found pistol ammo.");
        }
        if (policesh == 1) //found shotgun ammo
        {
            ammo[1] = ammo[1] + 6;
            System.out.println("Found shotgun ammo.");
        }
        if (policea == 1) //found assault rifle ammo
        {
            ammo[2] = ammo[2] + 30;
            System.out.println("Found assault rifle ammo.");
        }
        if (policesn == 1) //found sniper rifle ammo
        {
            ammo[3] = ammo[3] + 5;
            System.out.println("Found sniper rifle ammo.");
        }
    }

    public static void militaryscav()
    {
        if (militaryw == 1) //found weapon
        {
            scavwea = scavwea + 1; //increases weapon level
            weaponlevels(); //runs weapon scavenging
        }
        if (militaryp == 1) //found pistol ammo
        {
            ammo[0] = ammo[0] + 8;
            System.out.println("Found pistol ammo.");
        }
        if (militarysh == 1) //found shotgun ammo
        {
            ammo[1] = ammo[1] + 6;
            System.out.println("Found shotgun ammo.");
        }
        if (militarya == 1) //found assault rifle ammo
        {
            ammo[2] = ammo[2] + 30;
            System.out.println("Found assault rifle ammo.");
        }
        if (militarysn == 1) //found sniper rifle ammo
        {
            ammo[3] = ammo[3] + 5;
            System.out.println("Found sniper rifle ammo.");
        }
        if (SMWD == 1) //YOU FOUND THE SUPERIOR MASS WEAPONIZED DESTRUCTION 420 MM!!!!!!!1111!!!11
        {
            food = food + 420;
            weapon5 = ("SMWD CDXX");
            weapon = weapon5;
            scavwea = scavwea + 420;
            weaponammo = Integer.MAX_VALUE;
            System.out.println("You look through the ruins and find an odd-looking contraption. The best description you can give is an oversized rocket launcher. You examine the device and see the following printed on its side:");
            System.out.println(weapon5);
            System.out.println("Superior Mass Weaponized Destruction 420 mm - Energy-Directed Weapon");
            System.out.println("Cri-Fire Industries");
            System.out.println("You also find a vast stockpile of food at the base. By your guess, it's around 420 cans.");
        }
    }
    
    public static void weaponlevels() //in charge of weapon scavenging
    {
        if (scavwea == 2)
        {
            weapon2 = ("shotgun");
            weapon = (weapon2);
            System.out.println("You look through the pile of rubble and find a " + weapon2);
        }
        else if (scavwea == 3)
        {
            weapon3 = ("assault rifle");
            weapon = (weapon3);
            System.out.println("You look through the pile of rubble and find a " + weapon3);
        }
        else if (scavwea == 4)
        {
            weapon4 = ("sniper rifle");
            weapon = (weapon4);
            System.out.println("You look through the pile of rubble and find a " + weapon4);
        }
        else
        {
        }
    }
    
    public static void playerturn()
    {
        //sets starting magazine sizes
        if (weapon.equals(weapon0)) //knife 
        {
            weaponmag = Integer.MAX_VALUE;
            weaponammo = Integer.MAX_VALUE;
        }
        else if (weapon.equals(weapon1)) //pistol
        {
            weaponmag = 8;
            weaponammo = ammo[0];
        }
        else if (weapon.equals(weapon2)) //shotgun
        {
            weaponmag = 2;
            weaponammo = ammo[1];
        }
        else if (weapon.equals(weapon3)) //assault rifle
        {
            weaponmag = 30;
            weaponammo = ammo[2];
        }
        else if (weapon.equals(weapon4)) //sniper rifle
        {
            weaponmag = 5;
            weaponammo = ammo[3];
        }
        else if (weapon.equals(weapon5)) //SMWD CDXX
        {
            weaponmag = Integer.MAX_VALUE;
            weaponammo = Integer.MAX_VALUE;
        }
        
        System.out.println("IT'S TIME TO D-D-D-D-D-DUEL!");
        System.out.println("You have been confronted by a " + enemy + "!");
        System.out.println("What will you do?");
        battlemenu();
    }
    
    
    public static void battlemenu()
    {
        Random randomGenerator = new Random();
        Scanner scan = new Scanner (System.in);
        if (weaponammo == 0) //if equipped weapon is out of ammo, force switch
        {
            System.out.println("You're out of ammo for this weapon! Select another weapon to switch to:");
            weaponchange();
        }
        else if (weaponmag == 0) //if equipped weapon's mag is depleted, reload
        {
            System.out.println("You are reloading!\n...");
            if (weapon.equals(weapon0)) //knife
            {
                weaponmag = Integer.MAX_VALUE;
                enemyturn();
            }
            else if (weapon.equals(weapon1)) //pistol
            {
                weaponmag = 8;
                enemyturn();
            }
            else if (weapon.equals(weapon2)) //shotgun
            {
                weaponmag = 2;
                enemyturn();
            }
            else if (weapon.equals(weapon3)) //assault rifle
            {
                weaponmag = 30;
                enemyturn();
            }
            else if (weapon.equals(weapon4)) //sniper rifle
            {
                weaponmag = 5;
                enemyturn();
            }
        }
        
        if (playerdef == 30) //resets defense if player chose Defend last turn
        {
            System.out.println("You break out of your defensive stance, ready to fight.");
            playerdef = 20;
        }
    
        if (weaponmag > weaponammo) //if down to 1 mag or less, configure ammo display
        {
            weaponmag = weaponammo;
        }
        
        System.out.println("[A]ttack\n[D]efend\n[C]hange weapon (from " + weapon + ", " + weaponmag + "/" + weaponammo + " ammo)"); //normal battle choices
        playerturn = scan.nextLine();
        playerturn = playerturn.toLowerCase();
        if (playerturn.equals("a"))
        {
            if (weapon.equals(weapon0)) //knife
            {
                weapondmg = 20;
            }
            else if (weapon.equals(weapon1)) //pistol
            {
                weapondmg = 35;
            }
            else if (weapon.equals(weapon2)) //shotgun
            {
                weapondmg = 50;
            }
            else if (weapon.equals(weapon3)) //assault rifle
            {
                weapondmg = 40;
            }
            else if (weapon.equals(weapon4)) //sniper rifle
            {
                weapondmg = 70;
            }
            else if (weapon.equals(weapon5)) //SMWD CDXX
            {
                weapondmg = 420; //420 damage from blaaaaaaazing
            }
            
            weaponmag = weaponmag - 1; //depletes magazine 1 bullet at a time, same thing for ammo
            if ((weapon.equals(weapon0)) || (weapon.equals(weapon5))) 
            {
                weaponammo = weaponammo - 1;
            }
            else if (weapon.equals(weapon1))
            {
                ammo[0] = ammo[0] - 1;
                weaponammo = weaponammo - 1;
            }
            else if (weapon.equals(weapon2)) 
            {
                ammo[1] = ammo[1] - 1;
                weaponammo = weaponammo - 1;
            }
            else if (weapon.equals(weapon3)) 
            {
                ammo[2] = ammo[2] - 1;
                weaponammo = weaponammo - 1;
            }
            else if (weapon.equals(weapon4)) 
            {
                ammo[3] = ammo[3] - 1;
                weaponammo = weaponammo - 1;
            }
            
            if (weapondmg > enemydef) //if damage of equipped weapon is higher than enemy's defense
            {
                playerdmg = ((weapondmg - enemydef) + (randomGenerator.nextInt(20) -10 )); //player deals damage that's within 10 of their difference
                while (playerdmg <= 0)
                {
                    playerdmg = ((weapondmg - enemydef) + (randomGenerator.nextInt(20) -10 ));
                }
                enemyhp = enemyhp - playerdmg; //changes enemy's health
            }
            else if (weapondmg <= enemydef) //else if damage of equipped weapon is equal or less than enemy's defense
            {
                playerdmg = 1; //player deals 1 damage
                enemyhp = enemyhp - playerdmg; 
            }
            
            if (enemyhp > 0) //if enemy's health still exists, continue battle
            {
                System.out.println(playerdmg + " damage! Enemy HP: " + enemyhp + "\n...");
                enemyturn(); //goes to enemy turn
            }
            else  //otherwise, victory
            {
                System.out.println("Victory! You defeat the " + enemy + ".");
                enemyscav();
                kills = kills + 1; //adds kill
            }
        }
        else if (playerturn.equals("d")) //increases defense for one turn
        {
            System.out.println("You adopt a defensive stance.");
            playerdef = 30;
            enemyturn();
        }
        else if (playerturn.equals("c")) //runs weapon changing method
        {
            if (weapon5.equals("SMWD CDXX"))
            {
                System.out.println("Are you insane? You have an infinite ammo laser beam weapon that can kill anything and everyone with one shot! Get back out there and start blazing!\n...");
                battlemenu();
            }
            else
            {
                weaponchange();
            }
        }
        else //any other input
        {
            System.out.println("Command not recognized. Please try again.");
            battlemenu(); //returns to battle choices
        }
    }
    
    
    public static void weaponchange() //for changing weapons during battle
    {
        Scanner scan = new Scanner (System.in);
        if (scavwea == 1) //highest weapon is pistol
        {
            if (weapon.equals(weapon0))
            {
                System.out.println("[" + weapon1 + "], " + ammo[0] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon1))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[CANCEL]");
            }
        }
        else if (scavwea == 2) //highest weapon is shotgun
        {
            if (weapon.equals(weapon0))
            {
                System.out.println("[" + weapon1 + "], " + ammo[0] + " ammo\n[" + weapon2 + "], " + ammo[1] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon1))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[" + weapon2 + "], " + ammo[1] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon2))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[" + weapon1 + "], " + ammo[0] + " ammo\n[CANCEL]");
            }
        }
        else if (scavwea == 3) //highest weapon is assault rifle
        {
            if (weapon.equals(weapon0))
            {
                System.out.println("[" + weapon1 + "], " + ammo[0] + " ammo\n[" + weapon2 + "], " + ammo[1] + " ammo\n[" + weapon3 + "], " + ammo[2] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon1))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[" + weapon2 + "], " + ammo[1] + " ammo\n[" + weapon3 + "], " + ammo[2] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon2))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[" + weapon1 + "], " + ammo[0] + " ammo\n[" + weapon3 + "], " + ammo[2] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon3))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[" + weapon1 + "], " + ammo[0] + " ammo\n[" + weapon2 + "], " + ammo[1] + " ammo\n[CANCEL]");
            }
        }
        else if (scavwea == 4) //highest weapon is sniper rifle
        {
            if (weapon.equals(weapon0))
            {
                System.out.println("[" + weapon1 + "], " + ammo[0] + " ammo\n[" + weapon2 + "], " + ammo[1] + " ammo\n[" + weapon3 + "], " + ammo[2] + " ammo\n[" + weapon4 + "], " + ammo[3] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon1))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[" + weapon2 + "], " + ammo[1] + " ammo\n[" + weapon3 + "], " + ammo[2] + " ammo\n[" + weapon4 + "], " + ammo[3] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon2))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[" + weapon1 + "], " + ammo[0] + " ammo\n[" + weapon3 + "], " + ammo[2] + " ammo\n[" + weapon4 + "], " + ammo[3] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon3))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[" + weapon1 + "], " + ammo[0] + " ammo\n[" + weapon2 + "], " + ammo[1] + " ammo\n[" + weapon4 + "], " + ammo[3] + " ammo\n[CANCEL]");
            }
            else if (weapon.equals(weapon4))
            {
                System.out.println("[" + weapon0 + "], infinite ammo\n[" + weapon1 + "], " + ammo[0] + " ammo\n[" + weapon2 + "], " + ammo[1] + " ammo\n[" + weapon3 + "], " + ammo[2] + " ammo\n[CANCEL]");
            }
        }
        
        weaponswitch = scan.nextLine();
        weaponswitch = weaponswitch.toLowerCase();
        if (weaponswitch.equals("")) //if input is blank
        {
            System.out.println("Command not recognized. Please try again.");
            weaponchange(); //returns to weapon choices
        }
        else if (weaponswitch.equals(weapon)) //if input is identical to equipped weapon
        {
            System.out.println("You've already equipped this weapon!");
            weaponchange();
        }
        else if (weaponswitch.equals(weapon0)) //switch to knife
        {
            weapon = weapon0;
            weaponammo = Integer.MAX_VALUE;
            weaponmag = Integer.MAX_VALUE;
            System.out.println("You switch to your knife.\n...");
            enemyturn();
        }
        else if (weaponswitch.equals(weapon1)) //switch to pistol
        {
            weapon = weapon1;
            weaponammo = ammo[0];
            weaponmag = 8;
            System.out.println("You switch to your pistol.\n...");
            enemyturn();
        }
        else if (weaponswitch.equals(weapon2)) //switch to shotgun
        {
            weapon = weapon2;
            weaponammo = ammo[1];
            weaponmag = 2;
            System.out.println("You switch to your shotgun.\n...");
            enemyturn();
        }
        else if (weaponswitch.equals(weapon3)) //switch to assault rifle
        {
            weapon = weapon3;
            weaponammo = ammo[2];
            weaponmag = 30;
            System.out.println("You switch to your assault rifle.\n...");
            enemyturn();
        }
        else if (weaponswitch.equals(weapon4)) //switch to sniper rifle
        {
            weapon = weapon4;
            weaponammo = ammo[3];
            weaponmag = 50;
            System.out.println("You switch to your sniper rifle.\n...");
            enemyturn();
        }
        else if (weaponswitch.equals("cancel")) //cancel, go back
        {
            battlemenu();
        }
        else
        {
            System.out.println("Command not recognized. Please try again.");
            weaponchange(); //returns to weapon choices
        }
    }    
    

    public static void enemyscav()
    {
        Random randomGenerator = new Random();
        if (enemy.equals("Mutant"))
        {
        }
        else if (enemy.equals("Scout")) //picks up ammo from enemies
        {
            ammopickup = randomGenerator.nextInt((8) + 1);
            System.out.println("You pick up " + ammopickup + " " + enemyammodr + " ammo from the dead " + enemy + ".");
            ammo[0] = ammo[0] + ammopickup;
        }
        else if (enemy.equals("Hunter"))
        {
            ammopickup = randomGenerator.nextInt((6) + 1);
            ammo[1] = ammo[1] + ammopickup;
            if (scavwea == 1)
            {
                enemyweaponlevel(); //picks up better weapons from enemies
            }
            else
            {
                System.out.println("You pick up " + ammopickup + " " + enemyammodr + " ammo from the dead " + enemy + ".");
            }
        }
        else if (enemy.equals("Stormtrooper"))
        {
            ammopickup = randomGenerator.nextInt((30) + 1);
            ammo[2] = ammo[2] + ammopickup;
            if (scavwea == 2)
            {
                enemyweaponlevel(); 
            }
            else
            {
                System.out.println("You pick up " + ammopickup + " " + enemyammodr + " ammo from the dead " + enemy + ".");
            }
        }
        else if (enemy.equals("Sniper"))
        {
            ammopickup = randomGenerator.nextInt((5) + 1);
            ammo[3] = ammo[3] + ammopickup;
            if (scavwea == 3)
            {
                enemyweaponlevel();
            }
            else
            {
                System.out.println("You pick up " + ammopickup + " " + enemyammodr + " ammo from the dead " + enemy + ".");
            }
        }    
    }

    public static void enemyweaponlevel()
    {
        scavwea = scavwea + 1; //pick up weapon from enemy
        if (scavwea == 2)
        {
            weapon2 = ("shotgun");
            weapon = (weapon2);
            System.out.println("You pick up a " + weapon + " and " + ammopickup + " " + enemyammodr + " ammo from the dead " + enemy + ".");
        }
        else if (scavwea == 3)
        {
            weapon3 = ("assault rifle");
            weapon = (weapon3);
            System.out.println("You pick up a " + weapon + " and " + ammopickup + " " + enemyammodr + " ammo from the dead " + enemy + ".");
        }
        else if (scavwea == 4)
        {
            weapon4 = ("sniper rifle");
            weapon = (weapon4);
            System.out.println("You pick up a " + weapon + " and " + ammopickup + " " + enemyammodr + " ammo from the dead " + enemy + ".");
        }
        else
        {
        }
    }
    
    
    public static void enemyturn() //enemy battle turn
    {
        if (enemymag == 0) //if enemy depletes mag, they reload
        {
            if (enemy.equals("Scout")) //scout
            {
                enemymag = 8;
                System.out.println("The enemy is reloading!");
                battlemenu();
            }
            else if (enemy.equals("Hunter")) //hunter
            {
                enemymag = 2;
                System.out.println("The enemy is reloading!");
                battlemenu();
            }
            else if (enemy.equals("Stormtrooper")) //stormtrooper
            {
                enemymag = 30;
                System.out.println("The enemy is reloading!");
                battlemenu();
            }
            else if (enemy.equals("Sniper")) //sniper 
            {
                enemymag = 5;
                System.out.println("The enemy is reloading!");
                battlemenu();
            }
        }
        else if (enemymag != 0)
        {
            Random randomGenerator = new Random();
            enemymag = enemymag - 1; //depletes enemy mag 1 bullet at a time
            if (enemyatt > playerdef) //if enemy attack is higher than player defense
            {
                enemydmg = ((enemyatt - playerdef) + (randomGenerator.nextInt(20) -10 )); //enemy deals damage that's within 10 of their difference
                while (enemydmg <= 0)
                {
                    enemydmg = ((enemyatt - playerdef) + (randomGenerator.nextInt(20) -10 ));
                }
                hp = hp - enemydmg; //changes player's health
            }
            else if (enemyatt <= playerdef)
            {
                enemydmg = 1; //enemy deals 1 damage
                hp = hp - enemydmg; 
            }
            
            if (hp <= 0) //if player's health is depleted, death
            {
                System.out.println("You fight bravely, but the " + enemy + " overpowers you. YOU HAVE DIED.");
                System.exit(1);
            }
            else  //otherwise, continue battle
            {
                System.out.println(enemydmg + " health lost! HP: " + hp + "\n...");
                battlemenu();
            }
        }
    }
    
    
    public static void inventory()
    {
        Scanner scan = new Scanner (System.in);
        String cont;
        System.out.println("Food supplies: " + food);
        System.out.println("Weapons: " + weapon0 + "  " + weapon1 + "  " + weapon2 + "  " + weapon3 + "  " + weapon4 + "  " + weapon5); //lists weapons
        System.out.println("Ammo: " + ammo[0] + " Pistol, " + ammo[1] + " Shotgun, " + ammo[2] + " Assault Rifle, " + ammo[3] + " Sniper Rifle");
        System.out.println("(Enter anything to go back...)");
        cont = scan.nextLine(); 
    }
    
    
    public static void map()
    {
        Scanner scan = new Scanner (System.in);
        String cont;
        System.out.println("Your current location: (" + coord[0] + "," + coord[1] + ")");
        System.out.println("E7: (" + E7[0] + "," + E7[1] + ")");
        System.out.println("Police Stations: (" + police1[0] + "," + police1[1] + "), (" + police2[0] + "," + police2[1] + "), (" + police3[0] + "," + police3[1] + ")");
        System.out.println("Military Base: (" + military[0] + "," + military[1] + ")");
        System.out.println("Grocery Stores: (" + grocery1[0] + "," + grocery1[1] + "), (" + grocery2[0] + "," + grocery2[1] + "), (" + grocery3[0] + "," + grocery3[1] + ")");
        System.out.println("Enemy Camps: (" + camp1[0] + "," + camp1[1] + "), (" + camp2[0] + "," + camp2[1] + ")");
        System.out.println("(Enter anything to go back...)");
        cont = scan.nextLine();
    }
    
    
    public static void stats()
    {
        Scanner scan = new Scanner (System.in);
        String cont;
        System.out.println("Health: " + hp);
        System.out.println("Rest: " + rest);
        System.out.println("(Enter anything to go back...)");
        cont = scan.nextLine();
    }
    }


