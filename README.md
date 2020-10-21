## Air-Conditioner Remote

This is a Android application of Air-Conditioner's remote.
By send request to raspberry pi server and raspberry pi server send singal to air conditioner.


#### control.php?
- num+stat
  - 1 开关
    - 1 on
    - 0 off
  - 2 模式
    - 1 cooling
    - 0 heating
  - 3 温度
    - 1 higher
    - 0 lower
  - 4 风量
    - 1 down
    - 0 up 


#### config(build.gradle)
- android gradle plugin version
  3.6.3
- gradle version
  5.6.4
- target sdk 29(compile)
- min sdk 22
- build tools version 29.0.3
