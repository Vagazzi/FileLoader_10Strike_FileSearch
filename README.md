It is a software for loading files from 10-Strike Network Search Pro .html reports.

Requirements for working:
1. JRE 20.0.0 and higher.

Instruction for installation:
1. Unpack the archive into the root directory of your system disk;
2. Set an absolute path to directory .html reports into the outcomeURLsConfig.txt file;

/reports directory - directory with loaded files;
/URLs - directory with URLs of searched files;

# For big amount of files (>15k files) from reports strongly recommended use -Xms2048m -Xmx2048m VM options 

Issues:
1. If your system disk is not C:\, software will not work properly;
2. At version 1.0 software is able to work only with .html report files;
3. All of your .html reports must be only in one directory;
4. This software should be started on PC, on which was performed a scan with 10-Strike Network Search Pro,
or on PC into same subnetwork with network administrator rights;