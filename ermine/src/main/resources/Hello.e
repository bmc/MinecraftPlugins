module Hello where

import IO.Unsafe

hello p args = unsafePerformIO (sendMessage p "hello back")

goodbye p args = unsafePerformIO (sendMessage p "see ya")

foreign
  data "org.bukkit.entity.Player" Player
  method "sendMessage" sendMessage : Player -> String -> IO ()
