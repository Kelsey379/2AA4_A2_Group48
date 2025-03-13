package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private Drone drone; 
    private Action currAction; 
    private Direction startDir; 
    private BatteryStatus startBattery; 

    private State currState; 

    private final Logger logger = LogManager.getLogger();

    @Override
    //drone gets its heading, battery level ?? 
    public void initialize(String s) {



        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");


        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);


        this.startDir = new Direction(direction);
        this.startBattery = new BatteryStatus(batteryLevel);

        this.drone = new Drone(this.startDir, this.startBattery);


    }

    @Override
    // where the drone will take decisions 
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        decision.put("action", "stop"); // we stop the exploration immediately
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    @Override
    // where to store the decisions made my the drone 
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras"); 
        JSONObject biomesArray = extraInfo.getJSONObject("biomes"); 

        this.drone.updateDrone(response); // !!! 
        
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}