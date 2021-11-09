package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.model.dto.CreateTeamTO;
import com.xitaymin.tasktracker.model.dto.EditTeamTO;
import com.xitaymin.tasktracker.model.service.TeamService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping(TeamController.TEAMS)
public class TeamController {

    public static final String TEAMS = "/teams";

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping()
    public Team createTask(@Valid @RequestBody CreateTeamTO team) {
        return teamService.saveTeam(team);
    }

    @PutMapping()
    public void editTeam(@Valid @RequestBody EditTeamTO editTeamTO){
        teamService.editTeam(editTeamTO);
    }
}
