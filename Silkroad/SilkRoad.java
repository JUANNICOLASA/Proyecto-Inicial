import java.util.*;
import javax.swing.JOptionPane;

/**
 * Representa y gestiona la simulación visual de la Ruta de la Seda.
 * Esta clase se encarga de crear el lienzo, colocar tiendas y robots,
 * y animar sus movimientos. Actúa como el motor de simulación.
 */
public class SilkRoad {
    private int longitud;
    private List<Store> stores;
    private List<Robot> robots;
    private int profit;
    private boolean isVisible;
    private boolean lastOperationOk;
    private String lastError;
    private ProgressBar profitBar;
    private List<String> availableColors;
    private static final int STEP_SIZE = 20;
    private static final int START_X = 400;
    private static final int START_Y = 300;
    
    public SilkRoad(int longitud) {
        this.longitud = longitud;
        this.stores = new ArrayList<>();
        this.robots = new ArrayList<>();
        this.profit = 0;
        this.isVisible = false;
        this.lastOperationOk = true;
        this.lastError = "";
        this.profitBar = new ProgressBar();
        initializeColors();
    }
    
    public SilkRoad(int[][] storeData) {
        this(Arrays.stream(storeData).mapToInt(d -> d[0]).max().orElse(100));
        for (int[] data : storeData) {
            placeStore(data[0], data[1]);
        }
    }
    
    public void create(int[] data) {
        this.finish();
        if (data == null || data.length == 0) {
            this.lastOperationOk = false;
            this.lastError = "El array de entrada no puede ser nulo o vacío.";
            return;
        }

        int numRobots = data[0];
        int numStores = (data.length - 1 - numRobots) / 2;
        this.longitud = Arrays.stream(data).max().orElse(100); 

        int dataIndex = 1;
        for (int i = 0; i < numStores; i++) {
            placeStore(data[dataIndex], data[dataIndex + 1]);
            dataIndex += 2;
        }
        for (int i = 0; i < numRobots; i++) {
            placeRobot(data[dataIndex]);
            dataIndex++;
        }
        this.lastOperationOk = true;
    }

    public void moveRobot(int location, int meters) {
        Optional<Robot> robotToMoveOpt = robots.stream().filter(r -> r.getLocation() == location).findFirst();
        if (robotToMoveOpt.isPresent()) {
            Robot robot = robotToMoveOpt.get();
            int startLocation = robot.getLocation();
            int endLocation = startLocation + meters;

            if (endLocation < 0 || endLocation > this.longitud) {
                this.lastOperationOk = false;
                this.lastError = "El movimiento está fuera de los límites de la ruta.";
                return;
            }

            int totalCollectedOnPath = 0;
            int direction = (meters > 0) ? 1 : -1;

            for (int currentStep = startLocation; currentStep != endLocation; currentStep += direction) {
                int nextStepLocation = currentStep + direction; 
                final int stepLocation = nextStepLocation;
                Optional<Store> storeAtStep = stores.stream()
                                                    .filter(s -> s.getLocation() == stepLocation)
                                                    .findFirst();
                
                if (storeAtStep.isPresent()) {
                    int collected = storeAtStep.get().collectTenges();
                    if (collected > 0) {
                        totalCollectedOnPath += collected;
                    }
                }
            }

            robot.addTenges(totalCollectedOnPath);
            this.profit += totalCollectedOnPath;
            robot.recordMove(totalCollectedOnPath);

            if (isVisible) {
                int[] newCoords = calculateCoordinates(endLocation);
                robot.moveTo(endLocation, newCoords[0], newCoords[1]);
            } else {
                robot.move(endLocation);
            }

            if (isVisible && totalCollectedOnPath > 0) {
                updateProfitBar();
            }

            this.lastOperationOk = true;
        } else {
            this.lastOperationOk = false;
            this.lastError = "No se encontró un robot en la locación inicial especificada.";
        }
    }

    public void moveRobots() {
        boolean anyRobotMoved = false;
        for (Robot robot : robots) {
            int metersToMove = robot.decideNextMove(this.stores);
            if (metersToMove != 0) {
                moveRobot(robot.getLocation(), metersToMove);
                anyRobotMoved = true;
            }
        }
        
        if (!anyRobotMoved && !robots.isEmpty()) {
            this.lastOperationOk = false;
            this.lastError = "No hay movimiento beneficioso disponible para el robot";
        } else {
            this.lastOperationOk = true;
            this.lastError = "";
        }
        
        blinkTopRobot();
    }
    
    /**
     * Encuentra el robot con la mayor cantidad de tenges y lo hace parpadear.
     */
    public void blinkTopRobot() {
        if (isVisible && !robots.isEmpty()) {

            Robot topRobot = robots.stream()
                                   .max(Comparator.comparing(Robot::getTenges))
                                   .orElse(null);
            
            if (topRobot != null && topRobot.getTenges() > 0) {
                topRobot.blink();
            }
        }
    }

    public int[][] profitPerMove() {
        if (robots.isEmpty()) return new int[0][0];
        int maxMoves = robots.stream().mapToInt(r -> r.getProfitHistory().size()).max().orElse(0);
        List<Robot> sortedRobots = robots.stream().sorted(Comparator.comparingInt(Robot::getLocation)).toList();
        int[][] result = new int[sortedRobots.size()][maxMoves + 1];
        
        for (int i = 0; i < sortedRobots.size(); i++) {
            Robot r = sortedRobots.get(i);
            result[i][0] = r.getLocation(); 
            List<Integer> history = r.getProfitHistory();
            for (int j = 0; j < history.size(); j++) {
                result[i][j + 1] = history.get(j);
            }
        }
        return result;
    }
    
    public boolean ok() {
        return this.lastOperationOk;
    }
    
    public String getLastError() {
        return this.lastError;
    }
    
    private void initializeColors() {
        availableColors = new ArrayList<>(Arrays.asList("blue", "red", "green", "magenta", "orange", "cyan", "pink"));
    }

    private int[] calculateCoordinates(int location) {
        int x = START_X, y = START_Y;
        int direction = 0; 
        int stepsInSegment = 1;
        int stepCount = 0;
        int turnCount = 0;
        for (int i = 0; i < location; i++) {
            switch (direction) {
                case 0: x += STEP_SIZE; break; 
                case 1: y -= STEP_SIZE; break; 
                case 2: x -= STEP_SIZE; break; 
                case 3: y += STEP_SIZE; break; 
            }
            stepCount++;
            if (stepCount == stepsInSegment) {
                stepCount = 0;
                direction = (direction + 1) % 4;
                turnCount++;
                if (turnCount % 2 == 0) {
                    stepsInSegment++;
                }
            }
        }
        return new int[]{x, y};
    }
    
    public void placeStore(int location, int tenges) {
        if (stores.stream().anyMatch(s -> s.getLocation() == location)) {
            this.lastOperationOk = false;
            this.lastError = "Ya existe una tienda en esa ubicación.";
            return;
        }
        int[] coords = calculateCoordinates(location);
        Store newStore = new Store(location, tenges, getUniqueColor(), coords[0], coords[1]);
        stores.add(newStore);
        if (isVisible) {
            newStore.makeVisible();
        }
        this.lastOperationOk = true;
    }

    public void removeStore(int location) {
        Optional<Store> storeToRemove = stores.stream().filter(s -> s.getLocation() == location).findFirst();
        if (storeToRemove.isPresent()) {
            Store store = storeToRemove.get();
            if (isVisible) store.erase();
            stores.remove(store);
            availableColors.add(store.getColor());
            this.lastOperationOk = true;
        } else {
            this.lastOperationOk = false;
        }
    }

    public void placeRobot(int location) {
        if (robots.stream().anyMatch(r -> r.getInitialLocation() == location)) {
            this.lastOperationOk = false;
            return;
        }
        int[] coords = calculateCoordinates(location);
        Robot newRobot = new Robot(location, getUniqueColor(), coords[0], coords[1]);
        robots.add(newRobot);
        if (isVisible) newRobot.makeVisible();
        this.lastOperationOk = true;
    }

    public void removeRobot(int location) {
        Optional<Robot> robotToRemove = robots.stream().filter(r -> r.getLocation() == location).findFirst();
        if (robotToRemove.isPresent()) {
            Robot robot = robotToRemove.get();
            if (isVisible) robot.erase();
            robots.remove(robot);
            availableColors.add(robot.getColor());
            this.lastOperationOk = true;
        } else {
            this.lastOperationOk = false;
        }
    }
    
    public int[][] emptiedStores() {
        return stores.stream()
                     .map(s -> new int[]{s.getLocation(), s.getTimesEmptied()})
                     .sorted(Comparator.comparingInt(a -> a[0]))
                     .toArray(int[][]::new);
    }
    
    public void resupplyStores() {
        stores.forEach(Store::resupply);
        if (isVisible) updateProfitBar();
        this.lastOperationOk = true;
    }

    public void returnRobots() {
        for (Robot robot : robots) {
            if(isVisible){
                int[] initialCoords = calculateCoordinates(robot.getInitialLocation());
                robot.returnToStart(initialCoords[0], initialCoords[1]);
            } else {
                robot.returnToStart();
            }
        }
        this.lastOperationOk = true;
    }

    public void reboot() {
        this.profit = 0;
        resupplyStores();
        robots.forEach(Robot::resetTenges);
        returnRobots();
        if (isVisible) updateProfitBar();
        this.lastOperationOk = true;
    }

    public int profit() {
        return this.profit;
    }

    public int[][] stores() {
        return stores.stream().map(s -> new int[]{s.getLocation(), s.getTenges()}).sorted(Comparator.comparingInt(a -> a[0])).toArray(int[][]::new);
    }

    public int[][] robots() {
        return robots.stream().map(r -> new int[]{r.getLocation(), r.getTenges()}).sorted(Comparator.comparingInt(a -> a[0])).toArray(int[][]::new);
    }

    public void makeVisible() {
        this.isVisible = true;
        draw();
        stores.forEach(Store::makeVisible);
        robots.forEach(Robot::makeVisible);
    }
    
    public void makeInvisible() {
        this.isVisible = false;
        if (Canvas.getCanvas() != null) {
            Canvas.getCanvas().erase();
        }
    }

    public void finish() {
        makeInvisible(); 
        this.stores.clear();
        this.robots.clear();
        this.profit = 0;
        this.lastOperationOk = true;
        this.lastError = "";
        initializeColors();
    }
    
    private void draw() {
        int[] currentPos = {START_X, START_Y};
        for (int i = 0; i < this.longitud; i++) {
            int[] nextPos = calculateCoordinates(i + 1);
            Rectangle segment;
            if (currentPos[0] != nextPos[0]) {
                int startX = Math.min(currentPos[0], nextPos[0]);
                segment = new Rectangle(startX, currentPos[1] - 1, STEP_SIZE, 3);
            } else {
                int startY = Math.min(currentPos[1], nextPos[1]);
                segment = new Rectangle(currentPos[0] - 1, startY, 3, STEP_SIZE);
            }
            segment.changeColor("yellow");
            segment.makeVisible();
            currentPos = nextPos;
        }
        profitBar.makeVisible();
        updateProfitBar();
    }
    
    private void updateProfitBar() {
        int maxProfit = stores.stream().mapToInt(Store::getInitialTenges).sum();
        profitBar.update(profit, maxProfit);
    }
    
    private String getUniqueColor() {
        if (availableColors.isEmpty()) return "black";
        return availableColors.remove(0);
    }
}