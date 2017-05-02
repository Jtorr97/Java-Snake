//---------------------------------------------------------------------------------
// 
// Direction.java
//

// Enum for the snake's direction
public enum Direction {
    LEFT {
        Direction opposite() {
            return RIGHT;
        }
    },
    RIGHT {
        Direction opposite() {
            return LEFT;
        }
    },
    UP {
        Direction opposite() {
            return DOWN;
        }
    },
    DOWN {
        Direction opposite() {
            return UP;
        }
    };

    abstract Direction opposite();
}
