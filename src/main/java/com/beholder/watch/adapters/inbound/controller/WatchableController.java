package com.beholder.watch.adapters.inbound.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.beholder.watch.adapters.inbound.dtos.CreateWatchableDto;
import com.beholder.watch.dtos.outbound.WatchableOutput;
import com.beholder.watch.adapters.inbound.dtos.WatchableOutputDto;

import com.beholder.watch.model.watchable.Watchable;

import com.beholder.watch.usecases.WatchableUseCases;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("watchables")
@RequiredArgsConstructor
public class WatchableController {
        private final WatchableUseCases service;

        @PostMapping()
        public ResponseEntity<WatchableOutput> startWatch(
                        @Valid @RequestBody CreateWatchableDto request) {
                Watchable watchable = service.startWatch(request);

                WatchableOutputDto watchableOutput = WatchableOutputDto.builder()
                                .id(watchable.getId())
                                .name(watchable.getName())
                                .url(watchable.getUrl())
                                .checkInterval(watchable.getCheckInterval())
                                .status(watchable.getStatus())
                                .build();

                return new ResponseEntity<WatchableOutput>(watchableOutput, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<WatchableOutput> findById(@PathVariable Long id) {
                Watchable watchable = service.findById(id).orElseThrow(
                                () -> new RuntimeException("Watchable not found"));

                WatchableOutputDto watchableOutput = WatchableOutputDto.builder()
                                .id(watchable.getId())
                                .name(watchable.getName())
                                .url(watchable.getUrl())
                                .checkInterval(watchable.getCheckInterval())
                                .status(watchable.getStatus())
                                .build();

                return new ResponseEntity<WatchableOutput>(watchableOutput, HttpStatus.OK);
        }

}