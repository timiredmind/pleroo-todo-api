package com.timiremind.plerooo.core.util;

import java.util.List;

public record PageableMeta<T>(List<T> list, long total, int page, int limit, int totalPages) {}
