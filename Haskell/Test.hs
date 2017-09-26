type Ctordecl = (String {- ctor name -}, [String] {- ctor input types -})

dataTypeFromNames :: String {- the name of the Hawkid datatype -} -> [Ctordecl] -> String
dataTypeFromNames n cs = "something"

example :: [Ctordecl]
example = [("Zero",[]) , ("Suc",["Nat"])
