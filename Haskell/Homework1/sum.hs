tests = [ "1", "1 2 3", " 1", "1 ", "\t1\t", "\t12345\t", "010 020 030"
        , " 123 456 789 ", "-1", "-1 -2 -3", "\t-12345\t", " -123 -456 -789 "
        , "\n1\t\n3   555  -1\n\n\n-5", "123\t\n\t\n\t\n321 -4 -40"
        ]

mustFail = ["asd", "1-1", "1.2", "--2", "+1", "1+"]

advancedTests    = [ "+1", "1 +1", "-1 +1", "+1 -1"]
advancedMustFail = ["1+1", "++1", "-+1", "+-1", "1 + 1"]

stringSum :: String -> Int
stringSum line = foldr1 (+) (split line)

split :: String -> [Int]
split line = map number $ words line

number :: String -> Int
number l@(x:xs)
    | x == '-'  = negate (read xs)
    | x == '+'  = read xs
    | otherwise = read l  


ans1 = map stringSum tests
ans2 = map stringSum mustFail

ans3 = map stringSum advancedTests
ans4 = map stringSum advancedMustFail
