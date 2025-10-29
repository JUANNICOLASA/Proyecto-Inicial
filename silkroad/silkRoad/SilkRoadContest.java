package silkRoad;

import shapes.Canvas;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Resuelve y simula el problema de la maratón.
 */
public class SilkRoadContest {

    public int[] solve(int[][] days) {
        ArrayList<RobotState> robots = new ArrayList<>();
        ArrayList<StoreState> stores = new ArrayList<>();
        int[] dailyProfits = new int[days.length];
        int totalProfit = 0;

        for (int i = 0; i < days.length; i++) {
            int[] dayData = days[i];
            if (dayData[0] == 1) {
                robots.add(new RobotState(dayData[1]));
            } else {
                stores.add(new StoreState(dayData[1], dayData[2]));
            }

            List<StoreState> availableStores = stores.stream()
                                                     .filter(s -> !s.isEmpty())
                                                     .collect(Collectors.toList());
            Set<RobotState> assignedRobots = new HashSet<>();
            
            availableStores.sort(Comparator.comparingInt(StoreState::getTenges).reversed());

            for (StoreState store : availableStores) {
                RobotState closestAvailableRobot = null;
                int minDistance = Integer.MAX_VALUE;

                for (RobotState robot : robots) {
                    if (!assignedRobots.contains(robot)) {
                        int distance = Math.abs(robot.getLocation() - store.getLocation());
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestAvailableRobot = robot;
                        }
                    }
                }
                
                if (closestAvailableRobot != null) {
                    totalProfit += store.getTenges();
                    closestAvailableRobot.setLocation(store.getLocation());
                    store.collectTenges();
                    assignedRobots.add(closestAvailableRobot);
                }
            }
            
            dailyProfits[i] = totalProfit;
        }

        return dailyProfits;
    }

    /**
     * Simula la solución.
     */
    public void simulate(int[][] days, boolean slow) {
        SilkRoad simulation = new SilkRoad(100); 
        simulation.makeVisible();

        ArrayList<RobotState> robots = new ArrayList<>();
        ArrayList<StoreState> stores = new ArrayList<>();
        
        int waitTime = slow ? 1500 : 0; 

        for (int[] dayData : days) {
            if (dayData[0] == 1) {
                robots.add(new RobotState(dayData[1]));
                simulation.placeRobot("normal", dayData[1]); 
            } else {
                stores.add(new StoreState(dayData[1], dayData[2]));
                simulation.placeStore("normal", dayData[1], dayData[2]); 
            }
            
            if (slow) {
                Canvas.getCanvas().wait(waitTime);
            }

            List<StoreState> availableStores = stores.stream()
                                                     .filter(s -> !s.isEmpty())
                                                     .collect(Collectors.toList());
            Set<RobotState> assignedRobots = new HashSet<>();
            availableStores.sort(Comparator.comparingInt(StoreState::getTenges).reversed());

            for (StoreState store : availableStores) {
                RobotState closestAvailableRobot = null;
                int minDistance = Integer.MAX_VALUE;

                for (RobotState robot : robots) {
                    if (!assignedRobots.contains(robot)) {
                        int distance = Math.abs(robot.getLocation() - store.getLocation());
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestAvailableRobot = robot;
                        }
                    }
                }
                
                if (closestAvailableRobot != null) {
                    int startLocation = closestAvailableRobot.getLocation();
                    int endLocation = store.getLocation();
                    
                    simulation.moveRobot(startLocation, endLocation - startLocation, slow);
                    
                    closestAvailableRobot.setLocation(endLocation);
                    store.collectTenges();
                    assignedRobots.add(closestAvailableRobot);
                }
            }
            
            if (slow) {
                simulation.blinkTopRobot();
                Canvas.getCanvas().wait(waitTime);
            }
        }
        
        if (!slow) {
            simulation.blinkTopRobot();
        }
    }
    
    private class RobotState {
        private int location;
        RobotState(int location) { this.location = location; }
        int getLocation() { return location; }
        void setLocation(int location) { this.location = location; }
    }

    private class StoreState {
        private int location;
        private int tenges;
        StoreState(int location, int tenges) { this.location = location; this.tenges = tenges; }
        int getLocation() { return location; }
        int getTenges() { return tenges; }
        void collectTenges() { this.tenges = 0; }
        boolean isEmpty() { return this.tenges == 0; }
    }
}
