(defmodule SUE-ACTIONS

	(import SECTIONS ?ALL-SECTIONS ?SECTION-STATUS)

	;FACTS ASSERTED BY GAME INPUT
	(deftemplate BLINKY
		(slot edible (type SYMBOL))
		(slot nearGhostEdible (type SYMBOL) (allowed-symbols INKY PINKY SUE NONE))
		(slot nearGhostChasing (type SYMBOL) (allowed-symbols INKY PINKY SUE NONE))
		(slot nearGhostEdibleDistance (type NUMBER))
		(slot nearGhostChasingDistance (type NUMBER))
		(slot securePath (type SYMBOL))
		(slot pacmanClosestPPillDistance (type NUMBER))
		(slot pacmanDistance (type NUMBER))
	)
		
	(deftemplate INKY
		(slot edible (type SYMBOL))
		(slot nearGhostEdible (type SYMBOL) (allowed-symbols BLINKY PINKY SUE)) 
		(slot nearGhostChasing (type SYMBOL) (allowed-symbols BLINKY PINKY SUE)) 
		(slot nearGhostEdibleDistance (type NUMBER))
		(slot nearGhostChasingDistance (type NUMBER))
		(slot securePath (type SYMBOL))
		(slot pacmanClosestPPillDistance (type NUMBER))
		(slot pacmanDistance (type NUMBER))
	)
		
	(deftemplate PINKY
		(slot edible (type SYMBOL))
		(slot nearGhostEdible (type SYMBOL) (allowed-symbols BLINKY INKY SUE)) 
		(slot nearGhostChasing (type SYMBOL) (allowed-symbols BLINKY INKY SUE)) 
		(slot nearGhostEdibleDistance (type NUMBER))
		(slot nearGhostChasingDistance (type NUMBER))
		(slot securePath (type SYMBOL))
		(slot pacmanClosestPPillDistance (type NUMBER))
		(slot pacmanDistance (type NUMBER))

	)

	(deftemplate SUE
		(slot edible (type SYMBOL))
		(slot nearGhostEdible (type SYMBOL) (allowed-symbols BLINKY INKY PINKY)) 
		(slot nearGhostChasing (type SYMBOL) (allowed-symbols BLINKY INKY PINKY)) 
		(slot nearGhostEdibleDistance (type NUMBER))
		(slot nearGhostChasingDistance (type NUMBER))
		(slot securePath (type SYMBOL))
		(slot pacmanClosestPPillDistance (type NUMBER))
		(slot pacmanDistance (type NUMBER))

	)
		
	(deftemplate MSPACMAN 
		(slot closestPPill (type NUMBER))
		(slot mindistancePPill (type NUMBER))
		(slot nextJunction (type NUMBER))
		(slot nearestGhostEdible (type SYMBOL) (allowed-symbols SUE INKY PINKY SUE)) 
		(slot nearestGhostChasing (type SYMBOL) (allowed-symbols SUE INKY PINKY SUE))
		
	)
		
	;DEFINITION OF THE ACTION FACT
	(deftemplate ACTION
		(slot id) (slot info (default "")) (slot priority (type NUMBER) ) ; mandatory slots
		(slot runawaystrategy (type SYMBOL)) ; Extra slot for the runaway action
		(slot ghost (type SYMBOL)(allowed-symbols SUE INKY PINKY SUE))
		(slot powerpill (type NUMBER))
		(slot junction (type NUMBER))
		(slot section (type SYMBOL)(allowed-symbols section1 section2 section3 section4))
	) 

	;RULES 
	(defrule SUErunsAwayMSPACMANclosePPill
		(SUE (edible false) (pacmanClosestPPillDistance ?dp))
		(MSPACMAN (closestPPill ?p) (mindistancePPill ?d)) (>= ?dp ?d)))
		=>  
		(assert 
			(ACTION (id SUErunsAway) (info "MSPacMan cerca PPill") (priority 50) 
			)
		)
	)

	(defrule SUErunsAway
		(SUE (edible true)) 
		=>  
		(assert 
			(ACTION (id SUErunsAway) (info "Comestible --> huir") (priority 30) 
				(runawaystrategy CORNER)
			)
		)
	)
		
	(defrule SUEchases
		(SUE (edible false)) 
		=> 
		(assert (ACTION (id SUEchases) (info "No comestible --> perseguir")  (priority 10) ))
	)	
		
	(defrule SUErunsAwayEdibleGhosts	
		(SUE (edible true) (nearGhostEdible ?g) (nearGhostEdibleDistance ?d))
		(test (< ?d 20))
		=> 
		(assert (ACTION (id SUErunsAwayEdibleGhosts) (info "Cerca de ghost comestible --> huir")  (priority 50) 
		(ghost ?g))
		)
	)	

	(defrule SUErunsToProtectingGhosts	
		(SUE (edible true) (nearGhostChasing ?g) (nearGhostEdibleDistance ?d) (securePath true))
		(test (> ?d 20))
		=> 
		(assert (ACTION (id SUErunsToProtectingGhosts) (info "Camino seguro a ghost protecting --> me protegen")  (priority 50) 
		(ghost ?g))
		)
	)	

	(defrule SUEblocksPowerPills	
		(MSPACMAN (mindistancePPill ?d) (closestPPill ?p))
		(SUE (edible false) (pacmanClosestPPillDistance ?dp))
		(test (< ?dp ?d))
		=> 
		(assert (ACTION (id SUEblocksPowerPills) (info "Mas cerca de ppill que pacman --> proteger ppill")  (priority 50) 
		(powerpill ?p))
		)
	)

	(defrule SUEblocksNextJunction
		(MSPACMAN (nextJunction ?j) (nearestGhostChasing ?g)
		(SUE (edible false) (pacmanDistance ?d))
		(test (!= ?g SUE)(<= ?d 30)
		=> 
		(assert (ACTION (id  SUEblocksNextJunction) (info "Cerca de pacman pero no perseguidor principal --> bloquear junction")  (priority 50) 
		(junction ?j))
		)
	)

	(defrule SUEprotect
		(SUE (edible false) (nearGhostEdible ?n))
		(test (!= ?n NONE)
		=> 
		(assert (ACTION (id  SUEprotect) (info "Protejo a mi pana")  (priority 50) 
		(nearGhostEdible ?n))
		)
	)

	(defrule SUEblockSection
		(SUE (edible false) (pacmanDistance ?d))
		(test (>= ?d 30)
		=> 
		(assert (ACTION (id  SUEblockSection) (info "Bloqueo la seccion")  (priority 60) 
		)
	)

	(defrule SUERunToSection
		(SUE (edible true) (pacmanDistance ?d))
		(test (>= ?d 30)
		=> 
		(assert (ACTION (id  SUERunToSection) (info "Huir hacia la seccion")  (priority 60) 
		)
	)

	(defrule SUEUpdateSection
		?s <- (?ALL-SECTIONS (section ?section))
		(SUE (section ?currentSection))
		(or
			(and (test (neq ?currentSection "NONE"))
				(test (neq ?currentSection ?section))
				=>
				(modify ?s (?SECTION-STATUS (section ?currentSection) (status valid) (occupied busy)))
			)
			(and (test (eq ?currentSection "NONE"))
				(not (SECTION-STATUS (section ?section) (occupied busy)))
				=>
				(bind ?newSection (first $?section))
				(modify ?s (?SECTION-STATUS (section ?newSection) (status valid) (occupied busy)))
			)
			(and (test (eq ?currentSection "NONE"))
				=>
				(modify ?s (?SECTION-STATUS (section "NONE") (status valid) (occupied none)))
			)
		)
	)

	(defrule SUESectionActions
		?s <- (?ALL-SECTIONS (section ?section))
		(SUE (section ?section&~"NONE"))
		(SUERunToSection)
		=>
		(assert (ACTION (id SUESectionActions) (section ?section)))
	)
)