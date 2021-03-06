package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dto.TeamViewTO;
import com.xitaymin.tasktracker.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.dto.team.EditTeamTO;
import com.xitaymin.tasktracker.service.TeamService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController()
@RequestMapping(TeamController.TEAMS)
public class TeamController {

    public static final String TEAMS = "/teams";

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping()
    public TeamViewTO createTeam(@Valid @RequestBody CreateTeamTO team) {
        return teamService.saveTeam(team);
    }

    @PutMapping()
    public void editTeam(@Valid @RequestBody EditTeamTO editTeamTO) {
        teamService.editTeam(editTeamTO);
    }

    @GetMapping("/{id}")
    public TeamViewTO getTeam(@PathVariable @Positive long id) {
        return teamService.getTeam(id);
    }

    @GetMapping()
    public Collection<TeamViewTO> getAllTeams() {
        return teamService.getAllTeams();
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable @Positive long id) {
        teamService.deleteTeam(id);
    }

    @PutMapping("/{team}/member/{user}")
    public void addMember(@PathVariable @Positive long team, @PathVariable @Positive long user) {
        teamService.addMember(team, user);
    }

    @PutMapping("/{team}/lead/{lead}")
    public void setLead(@PathVariable @Positive long team, @PathVariable @Positive long lead){
        teamService.setLead(team,lead);
    }
}
