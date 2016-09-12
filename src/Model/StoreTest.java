package Model;

import main.Constants;

import static org.junit.Assert.*;

/**
 * Created by Nate on 11/4/2015.
 */
public class StoreTest {

    @org.junit.Test
    public final void testPurchaseMule(){
        Store.getInstance().newGame(0, 0, 0, 0, 1);
        final Player p = new Player("player", Player.Color.Blue, Player.Race.Bonzoid, Constants.PLAYER_START_MONEY, 0, 0, 0);
        assertTrue(Store.getInstance().purchaseMule(p, "Food"));
        assertTrue(p.getCurrentMule() != null);
        assertEquals(p.getMoney(), Constants.PLAYER_START_MONEY - Constants.STORE_PRICE_MULE - Constants.STORE_UPGRADEPRICE_FOOD);
    }

    @org.junit.Test
    public final void testPurchaseMuleNoInventory(){
        Store.getInstance().newGame(0, 0, 0, 0, 0);
        final Player p = new Player("player", Player.Color.Blue, Player.Race.Bonzoid, Constants.PLAYER_START_MONEY, 0, 0, 0);
        assertFalse(Store.getInstance().purchaseMule(p, "Food"));
        assertTrue(p.getCurrentMule() == null);
        assertEquals(p.getMoney(), Constants.PLAYER_START_MONEY);
    }

    @org.junit.Test
    public final void testPurchaseMuleNotEnoughMoney(){
        Store.getInstance().newGame(0, 0, 0, 0, 1);
        final Player p = new Player("player", Player.Color.Blue, Player.Race.Bonzoid, 0, 0, 0, 0);
        assertFalse(Store.getInstance().purchaseMule(p, "Food"));
        assertTrue(p.getCurrentMule() == null);
        assertEquals(p.getMoney(), 0);
    }


}