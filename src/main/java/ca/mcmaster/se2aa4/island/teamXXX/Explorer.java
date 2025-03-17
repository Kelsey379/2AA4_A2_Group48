package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;
import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {
    public Algorithm algorithm; 
    private Drone drone; 
    private Island island; 
    private Action currAction; 
    private Direction startDir;


    private StateMachine currState;

    private final Logger logger = LogManager.getLogger();

    @Override
    //drone gets its heading, battery level ?? 
    public void initialize(String s) {



        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        String direction = info.getString("heading"); // N, S, E, W
        this.startDir = Direction.setStartDir(direction); 
       
        Integer batteryLevel = info.getInt("budget");


        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);


        this.drone = new Drone(this.startDir, batteryLevel);
        this.currAction = this.drone.getCurrAction(); 
        this.island = new Island(); 
        this.currState = new StateMachine(this.drone, this.currAction, this.island, this.startDir); 

        this.algorithm = new Algorithm(this.drone, this.island, this.currState);





    }

    @Override
    //the state machine decides next action and then the decsion JSON object will be passed into the action class methods
    public String takeDecision() {

        JSONObject decision = new JSONObject();
        decision.put("action", "stop"); // we stop the exploration immediately
        logger.info("** Decision: {}",decision.toString());


        // String finalDecision = MissionControl.getRecentAction(); 
    
        return decision.toString();

        // String action = currState.executeState(); 
        // return action; 
        //add logic for states
    }

    @Override
    // where to store the decisions made my the drone 
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        // JSONObject extraInfo = response.getJSONObject("extras"); 
        // JSONObject biomesArray = extraInfo.getJSONObject("biomes"); 

        // this.currAction.updateDrone(response); // !!! --> Mission Control
        // this.currAction.updateIsland(response); // !!! --> Mission Control 

        missionControl.setResponse(response); 
        
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