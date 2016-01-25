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
package stevens.edu.infotrail.domain.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xshi90
 */
@Entity
@Table(name = "FLOOR_BOUNDARY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FloorBoundary.findAll", query = "SELECT f FROM FloorBoundary f"),
    @NamedQuery(name = "FloorBoundary.findByBoundaryId", query = "SELECT f FROM FloorBoundary f WHERE f.boundaryId = :boundaryId"),
    @NamedQuery(name = "FloorBoundary.findByFloorX", query = "SELECT f FROM FloorBoundary f WHERE f.floorX = :floorX"),
    @NamedQuery(name = "FloorBoundary.findByFloorId", query = "SELECT f FROM FloorBoundary f WHERE f.floorId.floorId = :floorId"),
    @NamedQuery(name = "FloorBoundary.findByFloorY", query = "SELECT f FROM FloorBoundary f WHERE f.floorY = :floorY")})
public class FloorBoundary implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "boundary_id")
    private Integer boundaryId;
    @Size(max = 10)
    @Column(name = "floor_x")
    private Integer floorX;
    @Size(max = 10)
    @Column(name = "floor_y")
    private Integer floorY;
    @JoinColumn(name = "floor_id", referencedColumnName = "floor_id")
    @ManyToOne(optional = false)
    private Floor floorId;

    public FloorBoundary() {
    }

    public FloorBoundary(Integer boundaryId) {
        this.boundaryId = boundaryId;
    }

    public Integer getBoundaryId() {
        return boundaryId;
    }

    public void setBoundaryId(Integer boundaryId) {
        this.boundaryId = boundaryId;
    }

    public int getFloorX() {
        return floorX;
    }

    public void setFloorX(int floorX) {
        this.floorX = floorX;
    }

    public int getFloorY() {
        return floorY;
    }

    public void setFloorY(int floorY) {
        this.floorY = floorY;
    }

    public Floor getFloorId() {
        return floorId;
    }

    public void setFloorId(Floor floorId) {
        this.floorId = floorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (boundaryId != null ? boundaryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FloorBoundary)) {
            return false;
        }
        FloorBoundary other = (FloorBoundary) object;
        if ((this.boundaryId == null && other.boundaryId != null) || (this.boundaryId != null && !this.boundaryId.equals(other.boundaryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "stevens.edu.infotrail.domain.entity.FloorBoundary[ boundaryId=" + boundaryId + " ]";
    }
    
}
