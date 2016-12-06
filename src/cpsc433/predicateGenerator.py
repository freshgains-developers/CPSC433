#generator for predicates
#cases:  number of people
        #percent of managers
        #num of groups
        #num of projects
        #percent of group heads
        #percent of project heads
            #double heads?
        #percent of secretaries
        #percent of smokers
        #percent of hackers
        #percent works with


        #number of rooms
        #number of rooms large
        #number of rooms small
        #percent rooms close to

import random

def makeSinglePredicate(x, y):
    #x is the predicate name, y is the input
    #returns string of the predicate so it can be written to file
    tempString = x + "(" + y + ")"
    return tempString


def makeDoublePredicate(x, y, z):
    #x is the predicate name, y and z are inputs
    #returns string of the predicate so it can be written to file
    tempString = x + "(" + y + "," + z + ")"
    return tempString


def main():
    #input all values
    numPeople = int(input("Number of People:  "))
    percManagers = int(input("Percent Managers:  "))
    percGroupHead = int(input("Percent Group Heads:  "))
    percProjectHead = int(input("Percent Project Heads:  "))
    percSecretaries = int(input("Percent Secretaries:  "))
    percSmokers = int(input("Percent Smokers:  "))
    percHackers = int(input("Percent Hackers:  "))
    percWorksWith = int(input("Percent Works With:  "))
    numProjects = int(input("Number of Projects:  "))
    numGroups = int(input("Number of Groups:  "))
    numRooms = int(input("Number of Rooms:  "))
    percLarge = int(input("Percent Large Rooms:  "))
    percSmall = int(input("Percent Small Rooms:  "))
    percCloseTo = int(input("Percent Close To:  "))

    #make file to write to
    f = open("input.txt", 'w') #overwrites everything in file

    #start making predicates
    f.write("\n")

    #make people
    for i in range(numPeople):
        tempString = makeSinglePredicate("person", "p" + str(i))
        f.write(tempString + "\n")
    f.write("\n")

    #make rooms
    for i in range(numRooms):
        tempString = makeSinglePredicate("room", "r" + str(i))
        f.write(tempString + "\n")
    f.write("\n")

    #make managers from the people
    numManagers = int((percManagers/100)* numPeople)
    for i in range(numManagers):
        tempString = makeSinglePredicate("manager", "p" + str(random.randint(0,numPeople-1)))
        f.write(tempString + "\n")
    f.write("\n")

    #make secretaries from the people
    numSecretaries = int((percSecretaries/100)* numPeople)
    for i in range(numSecretaries):
        tempString = makeSinglePredicate("secretary", "p" + str(random.randint(0,numPeople-1)))
        f.write(tempString + "\n")
    f.write("\n")

    #make smokers
    numSmokers = int((percSmokers/100)* numPeople)
    for i in range(numSmokers):
        tempString = makeSinglePredicate("smoker", "p" + str(random.randint(0,numPeople-1)))
        f.write(tempString + "\n")
    f.write("\n")

    #make hackers
    numHackers = int((percHackers/100)* numPeople)
    for i in range(numHackers):
        tempString = makeSinglePredicate("hacker", "p" + str(random.randint(0,numPeople-1)))
        f.write(tempString + "\n")
    f.write("\n")

    #make worksWith
    numWorksWith = int((percWorksWith/100)* numPeople)
    for i in range(numWorksWith):
        tempString = makeDoublePredicate("works-with" , "p" + str(random.randint(0,numPeople-1)) , "p" + str(random.randint(0,numPeople-1)))
        f.write(tempString + "\n")
    f.write("\n")

    #make projects
    for i in range(numProjects):
        tempString = makeSinglePredicate("project", "proj" + str(i))
        f.write(tempString + "\n")
    f.write("\n")

    #make groups
    for i in range(numGroups):
        tempString = makeSinglePredicate("group", "grp" + str(i))
        f.write(tempString + "\n")
    f.write("\n")

    #make projectHeads
    numProjectHeads = int((percProjectHead/100)* numPeople)
    for i in range(numProjectHeads):
        tempString = makeDoublePredicate("project" , "p" + str(random.randint(0,numPeople-1)) , "proj" + str(random.randint(0,numProjects-1)))
        f.write(tempString + "\n")
    f.write("\n")

    #make GroupHeads
    numGroupHeads = int((percGroupHead/100)* numPeople)
    for i in range(numGroupHeads):
        tempString = makeDoublePredicate("group" , "p" + str(random.randint(0,numPeople-1)) , "grp" + str(random.randint(0,numGroups-1)))
        f.write(tempString + "\n")
    f.write("\n")

    #make large rooms
    numLargeRooms = int((percLarge/100)* numRooms)
    for i in range(numLargeRooms):
        tempString = makeSinglePredicate("large-room", "r" + str(random.randint(0,numRooms-1)))
        f.write(tempString + "\n")
    f.write("\n")

    #make small rooms
    numSmallRooms = int((percSmall/100)* numRooms)
    for i in range(numSmallRooms):
        tempString = makeSinglePredicate("small-room", "r" + str(random.randint(0,numRooms-1)))
        f.write(tempString + "\n")
    f.write("\n")

    #make closeTo
    numCloseTo = int((percCloseTo/100)* numRooms)
    for i in range(numCloseTo):
        tempString = makeDoublePredicate("close" , "r" + str(random.randint(0,numRooms-1)) , "r" + str(random.randint(0,numRooms-1)))
        f.write(tempString + "\n")
    f.write("\n")


    f.close()

main()
