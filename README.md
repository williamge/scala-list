scala-list
==========

A simple implementation of a 'ls'-esque program made in Scala.

Usage follows the typical ls usage:

    ls [options] [path]
	
where options are the following:

	-a or --all: Displays all files or directories, including hidden files/directories

	-f: Files/directories will not be listed in a sorted order
	
	-l or --long: Lists files and directories in long form, displaying file permissions, size, modification date, and the name of the file or directory
	
	-r: Sorting is reversed
	
	-R: Recursively lists all folders found, 'ls -aR /' would accordingly list the entire filesystem	
	
	-S: Output is sorted by size in bytes
	
	-t: Output is sorted by last modification date
	
an example of the output is as follows:

	$ scala main.scala -lRS
	drwxr-xr-x	170	Fri Jul 26 21:25:02 EDT 2013	one
	drwxr-xr-x	68	Fri Jul 26 21:24:41 EDT 2013		b
	drwxr-xr-x	68	Fri Jul 26 21:25:02 EDT 2013		surprise
	drwxr-xr-x	102	Fri Jul 26 21:24:47 EDT 2013		two
	drwxr-xr-x	102	Fri Jul 26 21:24:50 EDT 2013			three
	drwxr-xr-x	102	Fri Jul 26 21:24:54 EDT 2013				c
	drwxr-xr-x	68	Fri Jul 26 21:24:54 EDT 2013					d
	-rw-r--r--	1232	Fri Jul 26 21:29:42 EDT 2013	README.md
	-rw-r--r--	5263	Fri Jul 26 21:29:49 EDT 2013	main.scala
