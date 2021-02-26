package com.sqisoft.testproject.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

@Slf4j
@Component
@Getter
@ToString
public class FfmpegHandler
{
	
}
