/*
*
* This class was made by HyChrod
* All rights reserved, 2016
*
*/
package de.HyChrod.Friends.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.OfflinePlayer;

public class BungeeSQL_Manager {

	public static Boolean playerExists(OfflinePlayer player) {
		try {
			ResultSet rs = MySQL.query("SELECT * FROM friends2_0_BUNGEE WHERE UUID= '" + player.getUniqueId().toString() + "'");
			
			if(rs.next()) {
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {}
		return false;
	}
	
	public static Boolean createPlayer(OfflinePlayer player) {
		if(!(playerExists(player))) {
			MySQL.update("INSERT INTO friends2_0_BUNGEE(UUID, ONLINE, SERVER, LASTONLINE) VALUES ('" + player.getUniqueId().toString() + "', '0', 'NOTHING', '');");
			if(playerExists(player)) {return true;}
			return false;
		}
		return true;
	}
	
	public static Long getLastOnline(OfflinePlayer player) {
		Long timeStamp = (long)0;
		if(playerExists(player)) {
			try {
				ResultSet rs = MySQL.query("SELECT * FROM friends2_0_BUNGEE WHERE UUID= '" + player.getUniqueId().toString() + "';");
				if((!rs.next()) || (String.valueOf(rs.getString("LASTONLINE")) == null));
				
				timeStamp = Long.parseLong(rs.getString("LASTONLINE"));
			} catch (Exception ex) {}
		}
		if(timeStamp < 5) {
			return SQL_Manager.getLastOnline(player);
		}
		return timeStamp;
	}
	
	public static void setLastOnline(OfflinePlayer player, Long timeStamp) {
		if(playerExists(player)) {
			MySQL.update("UPDATE friends2_0_BUNGEE SET LASTONLINE='" + timeStamp.toString() + "' WHERE UUID='" + player.getUniqueId().toString() + "';");
			return;
		} else {
			createPlayer(player);
			setLastOnline(player, timeStamp);
		};
	}
	
	public static void setServer(OfflinePlayer player, String server) {
		if(playerExists(player)) {
			MySQL.update("UPDATE friends2_0_BUNGEE SET SERVER='" + server + "' WHERE UUID='" + player.getUniqueId().toString() + "';");
		} else {
			createPlayer(player);
			setServer(player, server);
		}
		return;
	}
	
	public static String getServer(OfflinePlayer player) {
		if(playerExists(player)) {
			try {
				ResultSet rs = MySQL.query("SELECT * FROM friends2_0_BUNGEE WHERE UUID= '" + player.getUniqueId().toString() + "';");
				if((!rs.next()) || (String.valueOf(rs.getString("SERVER")) == null));
				String server = rs.getString("SERVER");
				
				return server;
				
			} catch (Exception e) {}
		}
		return "OFFLINE";
	}
	
	public static void setOnline(OfflinePlayer player, Integer value) {
		if(playerExists(player)) {
			MySQL.update("UPDATE friends2_0_BUNGEE SET ONLINE='" + value + "' WHERE UUID='" + player.getUniqueId().toString() + "';");
		} else {
			createPlayer(player);
			setOnline(player, value);
		}
		return;
	}
	
	public static boolean isOnline(OfflinePlayer player) {
		if(playerExists(player)) {
			try {
				ResultSet rs = MySQL.query("SELECT * FROM friends2_0_BUNGEE WHERE UUID= '" + player.getUniqueId().toString() + "';");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("ONLINE")) == null));
				
				Integer value =  rs.getInt("ONLINE");
				return value != 0;
				
			} catch (Exception e) {}
		}
		return false;
	}
	
}
