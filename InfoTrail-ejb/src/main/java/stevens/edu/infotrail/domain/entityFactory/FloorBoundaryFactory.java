/*
 * Copyright (C) 2015 xshi90
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.FloorBoundary;

/**
 *
 * @author xshi90
 */
public class FloorBoundaryFactory implements IFloorBoundaryFactory{

    @Override
    public FloorBoundary createFloorBoundary(int boundaryId, int floorX, int floorY, Floor floorId) {
        FloorBoundary floorBoundary = new FloorBoundary();
        floorBoundary.setBoundaryId(boundaryId);
        floorBoundary.setFloorX(floorX);
        floorBoundary.setFloorY(floorY);
        floorBoundary.setFloorId(floorId);
        return floorBoundary;
    }

    @Override
    public FloorBoundary getFloorBoundary() {
        return new FloorBoundary();
    }
    
}
