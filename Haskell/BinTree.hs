data BinTree a = Leaf | Node a (BinTree a) (BinTree a)

data DaysOfTheWeek = Sun | Mon | Tues

sampleBinTree :: BinTree Int
sampleBinTree = Node 5 (Node 3 Leaf (Node 4 Leaf Leaf)) (Node 6 Leaf Leaf)

binTreeToString :: Show a => BinTree a -> String
binTreeToString Leaf = "*"
binTreeToString (Node d l r) =
  "(" ++ (binTreeToString l) ++ " " ++ show d ++ " " ++ (binTreeToString r) ++ ")"

instance Show a => Show (BinTree a) where
  show = binTreeToString

rotateRight :: BinTree a -> BinTree a
rotateRight (Node d (Node d' x y) z) = Node d' x (Node d y z)
rotateRight x = x

depth :: BinTree a -> Int
depth Leaf = 0
depth (Node d l r) = 1 + (max (depth l) (depth r))

size :: BinTree a -> Int
size Leaf = 0
size (Node d l r) = 1 + size l + size r
