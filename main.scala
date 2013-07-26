import java.io._

 object slist {
 	//flags to be set from command line arguments
 	var list_parameter_ = false
 	var all_parameter_ = false
 	var path_ = new File(".").getCanonicalPath

 	/** Handles user interaction such as parameters. */
    def main(args: Array[String]) {
    	for (argument <- args){
    		if (argument.head == '-') argument match {
    			case "-l" | "--list" => list_parameter_ = true
    			case "-a" | "--all" => all_parameter_ = true
    		}
    		/* if it doesn't start with a '-' character then it otherwise must be the path, so we see if it's 
    		 * at the end of the arguments, in the correct spot */
    		else if ((args indexOf argument) == args.length - 1) path_ = argument
    	}
      list(path_)
    }

    /** Prints a given file according to formatting parameters.
      * @param item item to be listed 
      */
    def printFileItem(item: File)
    {
    	if (!item.isHidden() || all_parameter_) {
	    	if (item.isDirectory()) {
	    		list_parameter_ match {
	    			case false => print(item.getName() + " ")
	    			case true => println("d" + 
	    				{if (item.canRead()) "r" else "-"} + 
	    				{if (item.canWrite()) "w" else "-"} + 
	    				{if (item.canExecute()) "x" else "-"} +
	    				"\t" + item.getName())
	    		}
	    	} else if (item.isFile()) {
	    		list_parameter_ match {
	    			case false => print(item.getName() + " ")
	    			case true => println("-" + 
	    				{if (item.canRead()) "r" else "-"} + 
	    				{if (item.canWrite()) "w" else "-"} + 
	    				{if (item.canExecute()) "x" else "-"} +
	    				"\t" + item.getName())
	    		}
	    	}
	    }
    		
    }
    /** Handles the listing logic for each query.
      * @param path path to be listed
   	  */
    def list(path: String) {
    	try {
    		var currentDirectory = new File(path)

    		for (f <- currentDirectory.listFiles()) printFileItem(f)
    		if (!list_parameter_) println() //print a newline for formatting when each file doesn't get it's own line
    	} catch {
    		case _: NullPointerException => println("\"" + path_ + "\" is an invalid path")
    	}
    }
  }