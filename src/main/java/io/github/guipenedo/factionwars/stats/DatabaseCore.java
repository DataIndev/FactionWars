package io.github.guipenedo.factionwars.stats;

import java.sql.Connection;

public interface DatabaseCore {
  Connection getConnection();
  
  void queue(BufferStatement paramBufferStatement);
  
  void flush();
  
  void close();
}


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\stats\DatabaseCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */