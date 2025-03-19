package com.beholder.watch.dtos.outbound;

import com.beholder.watch.model.watchable.Watchable;
import java.util.Date;

public interface NotificationOutput {
  public Long getId();
  public Watchable getWatchable();
  public String getMessage();
  public Date getSentAt();
}
