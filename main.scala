import java.io._


 object slist {
 	var list_parameter_ = false
 	var all_parameter_ = false
 	var path_ = new File(".").getCanonicalPath

    def main(args: Array[String]) {
    	for (argument <- args){
    		if (argument.head == '-') argument match {
    			case "-l" | "--list" => list_parameter_ = true
    			case "-a" | "--all" => all_parameter_ = true
    		}
    		//if it doesn't start with a '-' character then it otherwise must be the path, so we see if it's at the end of the arguments, in the correct spot
    		else if ((args indexOf argument) == args.length - 1) path_ = argument
    	}
      list(path_)
    }

    def printFileItem(item: File)
    {
    	if (!item.isHidden()){
	    	if (item.isDirectory()) {
	    		list_parameter_ match {
	    			case false => print(item.getName() + " ")
	    			case true => println("\td\t" + item)
	    		}
	    	} else if (item.isFile()){
	    		list_parameter_ match {
	    			case false => print(item.getName() + " ")
	    			case true => println("\tf\t" + item)
	    		}
	    	}
	    }
    		
    }

    def list(path: String) {
    	try {
    		var currentDirectory = new File(path)

    		for (f <- currentDirectory.listFiles()) printFileItem(f)
    		if (!list_parameter_) println()
    	} catch {
    		case _: NullPointerException => println("\"" + path_ + "\" is an invalid path")
    	}
    }
  }