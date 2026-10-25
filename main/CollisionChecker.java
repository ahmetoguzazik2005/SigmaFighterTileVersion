package main;

import entity.Entity;

public class CollisionChecker
{
    GamePanel target;
    public  CollisionChecker(GamePanel gp){
        this.target = gp;
    }


    private boolean isSolid(int col, int row) {
        if (col < 0 || row < 0 || col >= target.maxWorldCol || row >= target.maxWorldRow) {
            return true;   // duvar
        }
        return target.tileM.tile[target.tileM.mapTileNumber[col][row]].collision;
    }
    public void checkTile(Entity entity) {

        double entityLeftWorldX   = entity.worldX + entity.solidArea.x;
        double entityRightWorldX  = entity.worldX + entity.solidArea.x + entity.solidArea.width - 1;
        double entityTopWorldY    = entity.worldY + entity.solidArea.y;
        double entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height - 1;

        int entityLeftCol   = Math.floorDiv((int) Math.floor(entityLeftWorldX), target.tileSize);
        int entityRightCol  = Math.floorDiv((int) Math.floor(entityRightWorldX), target.tileSize);
        int entityTopRow    = Math.floorDiv((int) Math.floor(entityTopWorldY), target.tileSize);
        int entityBottomRow = Math.floorDiv((int) Math.floor(entityBottomWorldY), target.tileSize);

        double speed = entity.speed;
        int nextTopRow, nextDownRow, nextLeftCol, nextRightCol;

        switch (entity.direction) {

            case UP:
                nextTopRow = Math.floorDiv((int) Math.floor(entityTopWorldY - speed), target.tileSize);
                if (isSolid(entityLeftCol, nextTopRow) || isSolid(entityRightCol, nextTopRow)) {
                    entity.collisionY = true;
                }
                break;

            case DOWN:
                nextDownRow = Math.floorDiv((int) Math.floor(entityBottomWorldY + speed), target.tileSize);
                if (isSolid(entityLeftCol, nextDownRow) || isSolid(entityRightCol, nextDownRow)) {
                    entity.collisionY = true;
                }
                break;

            case LEFT:
                nextLeftCol = Math.floorDiv((int) Math.floor(entityLeftWorldX - speed), target.tileSize);
                if (isSolid(nextLeftCol, entityTopRow) || isSolid(nextLeftCol, entityBottomRow)) {
                    entity.collisionX = true;
                }
                break;

            case RIGHT:
                nextRightCol = Math.floorDiv((int) Math.floor(entityRightWorldX + speed), target.tileSize);
                if (isSolid(nextRightCol, entityTopRow) || isSolid(nextRightCol, entityBottomRow)) {
                    entity.collisionX = true;
                }
                break;

            case UPRIGHT:
                nextTopRow   = Math.floorDiv((int) Math.floor(entityTopWorldY - speed), target.tileSize);
                nextRightCol = Math.floorDiv((int) Math.floor(entityRightWorldX + speed), target.tileSize);
                if (isSolid(entityLeftCol, nextTopRow) || isSolid(entityRightCol, nextTopRow)) {
                    entity.collisionY = true;
                }
                if (isSolid(nextRightCol, entityTopRow) || isSolid(nextRightCol, entityBottomRow)) {
                    entity.collisionX = true;
                }
                break;

            case UPLEFT:
                nextTopRow  = Math.floorDiv((int) Math.floor(entityTopWorldY - speed), target.tileSize);
                nextLeftCol = Math.floorDiv((int) Math.floor(entityLeftWorldX - speed), target.tileSize);
                if (isSolid(entityLeftCol, nextTopRow) || isSolid(entityRightCol, nextTopRow)) {
                    entity.collisionY = true;
                }
                if (isSolid(nextLeftCol, entityTopRow) || isSolid(nextLeftCol, entityBottomRow)) {
                    entity.collisionX = true;
                }
                break;

            case DOWNRIGHT:
                nextDownRow  = Math.floorDiv((int) Math.floor(entityBottomWorldY + speed), target.tileSize);
                nextRightCol = Math.floorDiv((int) Math.floor(entityRightWorldX + speed), target.tileSize);
                if (isSolid(entityLeftCol, nextDownRow) || isSolid(entityRightCol, nextDownRow)) {
                    entity.collisionY = true;
                }
                if (isSolid(nextRightCol, entityTopRow) || isSolid(nextRightCol, entityBottomRow)) {
                    entity.collisionX = true;
                }
                break;

            case DOWNLEFT:
                nextDownRow = Math.floorDiv((int) Math.floor(entityBottomWorldY + speed), target.tileSize);
                nextLeftCol = Math.floorDiv((int) Math.floor(entityLeftWorldX - speed), target.tileSize);
                if (isSolid(entityLeftCol, nextDownRow) || isSolid(entityRightCol, nextDownRow)) {
                    entity.collisionY = true;
                }
                if (isSolid(nextLeftCol, entityTopRow) || isSolid(nextLeftCol, entityBottomRow)) {
                    entity.collisionX = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {
        int index = -1;

        // PÜRÜZ 1: solidArea.x int, speed double. "-= entity.speed" derlenir ama
        // kesirli kısmı sessizce atar: 2.83 -> 2. Yani nesne kontrolü 2 piksel
        // ileriye bakarken hareket 2.83 piksel gidiyor, ikisi uyuşmuyor.
        // Yuvarlayıp tek bir int değer kullan.
        int spd = (int) Math.ceil(entity.speed);
        if (spd < 1) spd = 1;

        for (int i = 0; i < target.obj.length; i++) {
            if (target.obj[i] != null) {

                entity.solidArea.x = (int) entity.worldX + entity.solidAreaDefaultX;
                entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                target.obj[i].solidArea.x = target.obj[i].worldX + target.obj[i].solidAreaDefaultX;
                target.obj[i].solidArea.y = target.obj[i].worldY + target.obj[i].solidAreaDefaultY;

                switch (entity.direction) {
                    case UP:
                        entity.solidArea.y -= spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionY = true;
                            if (player) index = i;
                        }
                        break;

                    case DOWN:
                        entity.solidArea.y += spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionY = true;
                            if (player) index = i;
                        }
                        break;

                    case LEFT:
                        entity.solidArea.x -= spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionX = true;
                            if (player) index = i;
                        }
                        break;

                    case RIGHT:
                        entity.solidArea.x += spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionX = true;
                            if (player) index = i;
                        }
                        break;

                    case UPRIGHT:
                        entity.solidArea.y -= spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionY = true;
                            if (player) index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x += spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionX = true;
                            if (player) index = i;
                        }
                        break;

                    case UPLEFT:
                        entity.solidArea.y -= spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionY = true;
                            if (player) index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x -= spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionX = true;
                            if (player) index = i;
                        }
                        break;

                    case DOWNRIGHT:
                        entity.solidArea.y += spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionY = true;
                            if (player) index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x += spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionX = true;
                            if (player) index = i;
                        }
                        break;

                    case DOWNLEFT:
                        entity.solidArea.y += spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionY = true;
                            if (player) index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x -= spd;
                        if (entity.solidArea.intersects(target.obj[i].solidArea)) {
                            if (target.obj[i].collision) entity.collisionX = true;
                            if (player) index = i;
                        }
                        break;
                }

                target.obj[i].solidArea.x = target.obj[i].solidAreaDefaultX;
                target.obj[i].solidArea.y = target.obj[i].solidAreaDefaultY;
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
            }
        }

        return index;
    }

    // NPC OR MONSTER
    public int checkEntity(Entity entity, Entity[] target) {

        int index = -1;

// PÜRÜZ 1: solidArea.x int, speed double. "-= entity.speed" derlenir ama
// kesirli kısmı sessizce atar: 2.83 -> 2. Yani nesne kontrolü 2 piksel
// ileriye bakarken hareket 2.83 piksel gidiyor, ikisi uyuşmuyor.
// Yuvarlayıp tek bir int değer kullan.
        int spd = (int) Math.ceil(entity.speed);
        if (spd < 1) spd = 1;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                entity.solidArea.x = (int) entity.worldX + entity.solidAreaDefaultX;
                entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                target[i].solidArea.x = (int) target[i].worldX + target[i].solidAreaDefaultX;
                target[i].solidArea.y = (int) target[i].worldY + target[i].solidAreaDefaultY;

                switch (entity.direction) {
                    case UP:
                        entity.solidArea.y -= spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionY = true;
                            index = i;
                        }
                        break;

                    case DOWN:
                        entity.solidArea.y += spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionY = true;
                            index = i;
                        }
                        break;

                    case LEFT:
                        entity.solidArea.x -= spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionX = true;
                            index = i;
                        }
                        break;

                    case RIGHT:
                        entity.solidArea.x += spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionX = true;
                            index = i;
                        }
                        break;

                    case UPRIGHT:
                        entity.solidArea.y -= spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionY = true;
                            index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x += spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionX = true;
                            index = i;
                        }
                        break;

                    case UPLEFT:
                        entity.solidArea.y -= spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionY = true;
                            index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x -= spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionX = true;
                            index = i;
                        }
                        break;

                    case DOWNRIGHT:
                        entity.solidArea.y += spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionY = true;
                            index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x += spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionX = true;
                            index = i;
                        }
                        break;

                    case DOWNLEFT:
                        entity.solidArea.y += spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionY = true;
                            index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x -= spd;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            if (target[i].collision) entity.collisionX = true;
                            index = i;
                        }
                        break;
                }

                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
            }
        }

        return index;

    }

    // POW Collision of the npc
    public int checkPOWNpc(Entity entity, Entity[] target, Entity player) {

        int index = -1;

        int spd = (int) Math.ceil(entity.speed);
        if (spd < 1) spd = 1;

        // target dizisi + player, tek listede
        Entity[] all = new Entity[target.length + 1];
        System.arraycopy(target, 0, all, 0, target.length);
        all[target.length] = player;

        for (int i = 0; i < all.length; i++) {
            if (all[i] != null && all[i] != entity) {

                entity.solidArea.x = (int) entity.worldX + entity.solidAreaDefaultX;
                entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                all[i].solidArea.x = (int) all[i].worldX + all[i].solidAreaDefaultX;
                all[i].solidArea.y = (int) all[i].worldY + all[i].solidAreaDefaultY;

                switch (entity.direction) {
                    case UP:
                        entity.solidArea.y -= spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionY = true;
                            if (i <= target.length) index = i;
                        }
                        break;

                    case DOWN:
                        entity.solidArea.y += spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionY = true;
                            if (i <= target.length) index = i;
                        }
                        break;

                    case LEFT:
                        entity.solidArea.x -= spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionX = true;
                            if (i <= target.length) index = i;
                        }
                        break;

                    case RIGHT:
                        entity.solidArea.x += spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionX = true;
                            if (i <= target.length) index = i;
                        }
                        break;

                    case UPRIGHT:
                        entity.solidArea.y -= spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionY = true;
                            if (i <= target.length) index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x += spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionX = true;
                            if (i <= target.length) index = i;
                        }
                        break;

                    case UPLEFT:
                        entity.solidArea.y -= spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionY = true;
                            if (i <= target.length) index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x -= spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionX = true;
                            if (i <= target.length) index = i;
                        }
                        break;

                    case DOWNRIGHT:
                        entity.solidArea.y += spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionY = true;
                            if (i <= target.length) index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x += spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionX = true;
                            if (i <= target.length) index = i;
                        }
                        break;

                    case DOWNLEFT:
                        entity.solidArea.y += spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionY = true;
                            if (i <= target.length) index = i;
                        }
                        entity.solidArea.y = (int) entity.worldY + entity.solidAreaDefaultY;

                        entity.solidArea.x -= spd;
                        if (entity.solidArea.intersects(all[i].solidArea)) {
                            if (all[i].collision) entity.collisionX = true;
                            if (i <= target.length) index = i;
                        }
                        break;
                }

                all[i].solidArea.x = all[i].solidAreaDefaultX;
                all[i].solidArea.y = all[i].solidAreaDefaultY;
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
            }
        }

        return index;
    }
}
