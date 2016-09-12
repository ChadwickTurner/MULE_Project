package Model;

import Model.Locations.Location_Mountain1;
import Model.Locations.Location_Plain;
import Model.Locations.Location_River;
import Model.Mules.Mule_Energy;
import Model.Mules.Mule_Smithore;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import java.util.List;


/**
 * Created by Chad on 11/4/2015.
 */
public class SettleMuleTest {

    private Player p1;
    private Player p2;
    private Mule energy;
    private Mule smithore;
    private Location owned;
    private Location unowned;
    private Location occupied;

    @Before
    public final void setUp() {
        p1 = new Player("p1", Player.Color.Blue, Player.Race.Bonzoid,
                0, 0, 0, 0);
        p2 = new Player("p2", Player.Color.Green, Player.Race.Bonzoid,
                0, 0, 0, 0);

        energy = new Mule_Energy(p1);
        smithore = new Mule_Smithore(p2);

        p1.setCurrentMule(energy);
        owned = new Location_Plain();
        owned.setOwner(p1);

        unowned = new Location_Mountain1();

        occupied = new Location_River();
        occupied.setMule(smithore);
    }


    @Test
    public final void testSettleMule() {
        assertTrue(p1.settleMule(owned));
        assertTrue(owned.getMule() != null);
        assertEquals(owned.getMule(), energy);

        List<Mule> m = p1.getMules();
        assertFalse(m.isEmpty());
        assertEquals(m.size(), 1);
        assertEquals(m.get(0), energy);

        assertNull(p1.getCurrentMule());
    }

    @Test
    public final void testSettleMuleUnowned() {
        assertFalse(p1.settleMule(unowned));
        assertNull(unowned.getMule());

        List<Mule> m = p1.getMules();
        assertTrue(m.isEmpty());

        assertNull(p1.getCurrentMule());
    }

    @Test
    public final void testSettleMuleOccupied() {
        assertFalse(p1.settleMule(occupied));
        assertEquals(occupied.getMule(), smithore);

        List<Mule> m = p1.getMules();
        assertTrue(m.isEmpty());

        assertNull(p1.getCurrentMule());
    }

    @Test
    public final void testSettleMuleNoMule() {
        assertFalse(p2.settleMule(owned));
        assertNull(owned.getMule());

        List<Mule> m = p2.getMules();
        assertTrue(m.isEmpty());

        assertNull(p2.getCurrentMule());
    }
}